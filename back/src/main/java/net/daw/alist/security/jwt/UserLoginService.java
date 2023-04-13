package net.daw.alist.security.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private JwtCookieManager cookieUtil;

  public ResponseEntity<AuthResponse> login(LoginRequest loginRequest, String encryptedAccessToken, String
          encryptedRefreshToken) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String accessTokenValue = SecurityCipher.decrypt(encryptedAccessToken);
    String refreshTokenValue = SecurityCipher.decrypt(encryptedRefreshToken);

    String username = loginRequest.getUsername();
    UserDetails user = userDetailsService.loadUserByUsername(username);

    boolean accessTokenValid = jwtTokenProvider.validateToken(accessTokenValue);
    boolean refreshTokenValid = jwtTokenProvider.validateToken(refreshTokenValue);

    HttpHeaders responseHeaders = new HttpHeaders();
    Token newAccessToken;
    Token newRefreshToken;
    if (!accessTokenValid && !refreshTokenValid) {
      newAccessToken = jwtTokenProvider.generateToken(user);
      accessTokenValue = newAccessToken.getTokenValue();
      newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
      addAccessTokenCookie(responseHeaders, newAccessToken);
      addRefreshTokenCookie(responseHeaders, newRefreshToken);
    }
    else if (!accessTokenValid) {
      newAccessToken = jwtTokenProvider.generateToken(user);
      accessTokenValue = newAccessToken.getTokenValue();
      addAccessTokenCookie(responseHeaders, newAccessToken);
    }
    else if (refreshTokenValid) {
      newAccessToken = jwtTokenProvider.generateToken(user);
      accessTokenValue = newAccessToken.getTokenValue();
      newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
      addAccessTokenCookie(responseHeaders, newAccessToken);
      addRefreshTokenCookie(responseHeaders, newRefreshToken);
    }

    addAuthTokenHeader(responseHeaders, accessTokenValue);
    AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.SUCCESS,
            "Auth successful. Tokens are created in cookie.");
    return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
  }

  public ResponseEntity<AuthResponse> refresh(String encryptedRefreshToken) {

    String refreshTokenValue = SecurityCipher.decrypt(encryptedRefreshToken);

    boolean refreshTokenValid = jwtTokenProvider.validateToken(refreshTokenValue);

    if (!refreshTokenValid) {
      AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.FAILURE,
              "Invalid refresh token !");
      return ResponseEntity.ok().body(loginResponse);
    }

    String username = jwtTokenProvider.getUsername(refreshTokenValue);
    UserDetails user = userDetailsService.loadUserByUsername(username);

    Token newAccessToken = jwtTokenProvider.generateToken(user);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil
            .createAccessTokenCookie(newAccessToken.getTokenValue(), newAccessToken.getDuration()).toString());

    addAuthTokenHeader(responseHeaders, newAccessToken.getTokenValue());
    AuthResponse loginResponse = new AuthResponse(AuthResponse.Status.SUCCESS,
            "Auth successful. Tokens are created in cookie.");
    return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
  }

  public String logout(HttpServletRequest request, HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    SecurityContextHolder.clearContext();
    session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }

    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        cookie.setMaxAge(0);
        cookie.setValue("");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
      }
    }

    return "logout successfully";
  }

  private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
    httpHeaders.add(HttpHeaders.SET_COOKIE,
            cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
  }

  private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
    httpHeaders.add(HttpHeaders.SET_COOKIE,
            cookieUtil.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
  }

  private void addAuthTokenHeader(HttpHeaders httpHeaders, String tokenValue) {
    httpHeaders.add(HttpHeaders.AUTHORIZATION, tokenValue);
  }

}
