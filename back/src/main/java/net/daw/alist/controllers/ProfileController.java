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

  private User userProfile;
  private User userRepo;

  @GetMapping("/user/{username}")
  public String profile(
    Model model,
    HttpServletResponse httpResponse,
    Authentication authentication,
    @PathVariable String username
  ) throws IOException {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      userProfile = optionalUser.get();
      if (!isGuest(authentication)) {
        model.addAttribute("notGuest", true);
        if (isLoggedUser(username, authentication)) {
          model.addAttribute("ownProfile", true);
        } else {
          model.addAttribute("ownProfile", false);
          if (isFollowed()) {
            model.addAttribute("followed", true);
          } else {
            model.addAttribute("followed", false);
          }
        }
      }
      model.addAttribute("user", userProfile);
      return "profile";
    }
    httpResponse.sendRedirect("/error");
    return "error";
  }

  @GetMapping("/user/{username}/follow")
  public String follow(@PathVariable String username) {
    userRepo.follow(userProfile);
    userRepository.save(userRepo);
    return "redirect:/user/{username}";
  }

  @GetMapping("/user/{username}/unfollow")
  public String unfollow(@PathVariable String username) {
    userRepo.unFollow(userProfile);
    userRepository.save(userRepo);
    return "redirect:/user/{username}";
  }

  private boolean isGuest(Authentication authentication) {
    return authentication == null;
  }

  private boolean isLoggedUser(String username, Authentication authentication) {
    User userSession = (User) authentication.getPrincipal();
    userRepo = userRepository.findByUsername(userSession.getUsername()).orElseThrow();
    return Objects.equals(username, userSession.getUsername());
  }

  private boolean isFollowed() {
    return userRepo.getFollowing().contains(userProfile);
  }

}
