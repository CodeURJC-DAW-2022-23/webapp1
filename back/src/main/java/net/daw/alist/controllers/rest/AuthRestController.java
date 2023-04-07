package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.daw.alist.models.User;
import net.daw.alist.security.RegistrationRequest;
import net.daw.alist.security.jwt.AuthResponse;
import net.daw.alist.security.jwt.LoginRequest;
import net.daw.alist.security.jwt.UserLoginService;
import net.daw.alist.services.RegistrationService;
import net.daw.alist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static net.daw.alist.controllers.rest.UserRestController.getUserResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

  @Autowired
  private RegistrationService registrationService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserLoginService userLoginService;

  @Operation(summary = "Register a new user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "User created", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    }),
    @ApiResponse(responseCode = "403", description = "Username already taken", content = @Content),
    @ApiResponse(responseCode = "400", description = "Username/password is shorter than 4 characters or invalid email address", content = @Content)
  })
  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody RegistrationRequest request) throws SQLException, IOException {
    Optional<User> optionalUser = userService.findByUsername(request.getUsername());
    if (optionalUser.isEmpty()) {
      String response = registrationService.register(request);
      if (response.startsWith("Success")) {
        User user = userService.findByUsername(request.getUsername()).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
      }
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @Operation(summary = "Sign-in user with credentials")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Sign-in successful"),
    @ApiResponse(responseCode = "403", description = "Sign-in unsuccessful")
  })
  @PostMapping("/sign-in")
  public ResponseEntity<AuthResponse> login(
    @CookieValue(name = "accessToken", required = false) String accessToken,
    @CookieValue(name = "refreshToken", required = false) String refreshToken,
    @RequestBody LoginRequest loginRequest
  ) {
    return userLoginService.login(loginRequest, accessToken, refreshToken);
  }

  @Operation(summary = "Sign-out user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Sign-out successful")
  })
  @PostMapping("/sign-out")
  public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {
    return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(request, response)));
  }

  @Operation(summary = "Get logged user")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    }),
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
    @ApiResponse(responseCode = "500", description = "No user logged in", content = @Content),
  })
  @GetMapping("/logged-user")
  public ResponseEntity<User> userLogged(HttpServletRequest request) {
    String username = request.getUserPrincipal().getName();
    return getUserResponseEntity(username, userService);
  }

}
