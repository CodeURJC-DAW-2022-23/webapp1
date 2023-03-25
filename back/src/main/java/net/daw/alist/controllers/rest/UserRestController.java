package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.daw.alist.models.User;
import net.daw.alist.security.RegistrationRequest;
import net.daw.alist.services.RegistrationService;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

  @Autowired
  private UserService userService;

  @Autowired
  private Utils utils;

  @Autowired
  private RegistrationService registrationService;

  @Operation(summary = "Register a new user")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "User created", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
          @ApiResponse(responseCode = "403", description = "Username already taken", content = @Content),
          @ApiResponse(responseCode = "400", description = "Username/password is shorter than 4 characters or invalid email address", content = @Content)
  })
  @PostMapping("/")
  public ResponseEntity<User> register(@RequestBody RegistrationRequest request) throws SQLException, IOException {
    Optional<User> optionalUser = userService.findByUsername(request.getUsername());
    if (optionalUser.isEmpty()) {
      String response = registrationService.register(request);
      if (response.startsWith("Success")){
        User user = userService.findByUsername(request.getUsername()).orElseThrow();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
      } else{
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } else {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  @Operation(summary = "Get a user by its username")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User found", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  @GetMapping("/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    Optional<User> optionalUser = userService.findByUsername(username);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "(Admin) Ban/unban user by its id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User found and banned/unbanned state changed", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
          @ApiResponse(responseCode = "403", description = "Admin access required", content = @Content),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  @PutMapping ("/{id}")
  public ResponseEntity<User> banUser(Authentication authentication, @PathVariable Long id) {
    String userRole = utils.getCurrentUserRole(authentication);
    if (!userRole.equals("ADMIN"))
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    Optional<User> optionalUser = userService.findByID(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.isLocked()) {
        userService.unbanUser(user.getUsername());
      } else {
        userService.banUser(user.getUsername());
      }
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Current user follows another user by its name")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User found and followed/unfollowed state changed", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
          @ApiResponse(responseCode = "403", description = "Current user not logged in", content = @Content),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  @PutMapping("/{username}/follows")
  public ResponseEntity<User> follow(Authentication authentication, @PathVariable String username){
    Optional<User> optionalUser =  userService.findByUsername(username);
    User userSession = (User) authentication.getPrincipal();
    userSession  = userService.findByID(userSession.getId()).orElseThrow();
    if(optionalUser.isPresent()) {
      User userProfile = optionalUser.get();
      if (userProfile.getFollowers().contains(userSession))
        userSession.unFollow(userProfile);
      else
        userSession.follow(userProfile);
      userService.saveUser(userSession);
      return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
