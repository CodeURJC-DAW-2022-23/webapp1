package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.daw.alist.security.jwt.AuthResponse;
import net.daw.alist.security.jwt.LoginRequest;
import net.daw.alist.security.jwt.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

  @Autowired
  private UserLoginService userLoginService;

  @Operation(summary = "Sign-in user with credentials")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Sign-in successful"),
          @ApiResponse(responseCode = "403", description = "Sign-in unsuccessful")
  })
  @PostMapping("/sign-in")
  public ResponseEntity<AuthResponse> login(
          @CookieValue(name = "accessToken", required = false) String accessToken,
          @CookieValue(name = "refreshToken", required = false) String refreshToken,
          @RequestBody LoginRequest loginRequest) {
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

}
