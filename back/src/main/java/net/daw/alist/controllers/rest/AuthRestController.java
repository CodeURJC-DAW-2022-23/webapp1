package net.daw.alist.controllers.rest;

import net.daw.alist.security.jwt.AuthResponse;
import net.daw.alist.security.jwt.LoginRequest;
import net.daw.alist.security.jwt.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

  @Autowired
  private UserLoginService userService;

  @PostMapping("/sign-in")
  public ResponseEntity<AuthResponse> login(
          @CookieValue(name = "accessToken", required = false) String accessToken,
          @CookieValue(name = "refreshToken", required = false) String refreshToken,
          @RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest, accessToken, refreshToken);
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refreshToken(
          @CookieValue(name = "refreshToken", required = false) String refreshToken) {
    return userService.refresh(refreshToken);
  }

  @PostMapping("/sign-out")
  public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {
    return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userService.logout(request, response)));
  }

}
