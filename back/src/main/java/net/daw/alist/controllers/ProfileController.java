package net.daw.alist.controllers;

import net.daw.alist.models.User;
import net.daw.alist.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ProfileController {

  @Autowired
  UserRepository userRepository;

  private User user;
  private User userSession;

  @GetMapping("/user/{username}")
  public String profile(
    Model model,
    HttpServletResponse httpResponse,
    Authentication authentication,
    @PathVariable String username
  ) throws IOException {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      user = optionalUser.get();
      // TODO: guest
      if (isLoggedUser(username, authentication)) {
        model.addAttribute("ownProfile", true);
      } else {
        /* bad bc userSession = null
        if (isFollowed()) {
          model.addAttribute("followed", true);
        } else {
          model.addAttribute("followed", false);
        }
        */
        model.addAttribute("ownProfile", false);
      }
      model.addAttribute("user", user);
      return "profile";
    }
    httpResponse.sendRedirect("/error");
    return "error";
  }

  private boolean isLoggedUser(String username, Authentication authentication) {
    if (authentication == null) return false;
    userSession = (User) authentication.getPrincipal();
    return Objects.equals(username, userSession.getUsername());
  }

  private boolean isFollowed() {
    return userSession.getFollowing().contains(user);
  }

}
