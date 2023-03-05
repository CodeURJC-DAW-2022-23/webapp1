package net.daw.alist.controllers;

import net.daw.alist.models.User;
import net.daw.alist.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Controller
public class ProfileController {

  @Autowired
  UserRepository userRepository;

  private User userProfile;
  private User userRepo;

  @GetMapping("/profile")
  public String profile(Authentication authentication) {
    if (isNotGuest(authentication)) {
      String userSessionUsername = getUserSessionUsername(authentication);
      return "redirect:/user/" + userSessionUsername;
    }
    return "redirect:/";
  }

  @GetMapping("/user/{username}")
  public String user(
    Model model,
    Authentication authentication,
    @PathVariable String username
  ) {
    userProfile = userRepository.findByUsername(username).orElseThrow();
    if (isNotGuest(authentication)) {
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

  private boolean isNotGuest(Authentication authentication) {
    return authentication != null;
  }

  private boolean isLoggedUser(String username, Authentication authentication) {
    String userSessionUsername = getUserSessionUsername(authentication);
    userRepo = userRepository.findByUsername(userSessionUsername).orElseThrow();
    return Objects.equals(username, userSessionUsername);
  }

  private String getUserSessionUsername(Authentication authentication) {
    User userSession = (User) authentication.getPrincipal();
    return userSession.getUsername();
  }

  private boolean isFollowed() {
    return userRepo.getFollowing().contains(userProfile);
  }

}
