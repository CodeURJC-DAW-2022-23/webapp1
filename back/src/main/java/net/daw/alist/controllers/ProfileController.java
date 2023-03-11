package net.daw.alist.controllers;

import net.daw.alist.models.Post;
import net.daw.alist.models.User;

import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;

@Controller
public class ProfileController {

  @Autowired
  UserService userService;

  @Autowired
  PostService postService;

  private User userProfile;
  private User userSessionRepo;

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
    userProfile = (User) userService.loadUserByUsername(username);
    String userProfileUsername = userProfile.getUsername();
    if (!Objects.equals(userProfileUsername, username)) {
      return "redirect:/user/" + userProfileUsername;
    }
    if (isNotGuest(authentication)) {
      model.addAttribute("notGuest", true);
      if (isLoggedUser(username, authentication)) {
        model.addAttribute("ownProfile", true);
        model.addAttribute("admin", isAdmin());
      } else {
        model.addAttribute("ownProfile", false);
        model.addAttribute("followed", isFollowed());
      }
    }
    model.addAttribute("user", userProfile);
    int userProfileId = userProfile.getId().intValue();
    Page<Post> userProfilePosts = postService.getUserPosts(0, userProfileId);
    model.addAttribute("posts", userProfilePosts);
    return "profile";
  }

  @GetMapping("/user/{username}/following")
  public String following(Model model, @PathVariable String username) {
    model.addAttribute("follow", "Followed by");
    model.addAttribute("user", userProfile);
    model.addAttribute("followList", userProfile.getFollowing());
    return "follow";
  }

  @GetMapping("/user/{username}/followers")
  public String followers(Model model, @PathVariable String username) {
    model.addAttribute("follow", "Followers of");
    model.addAttribute("user", userProfile);
    model.addAttribute("followList", userProfile.getFollowers());
    return "follow";
  }

  @GetMapping("/user/{username}/follow")
  public String follow(@PathVariable String username) {
    userSessionRepo.follow(userProfile);
    userService.saveUser(userSessionRepo);
    return "redirect:/user/{username}";
  }

  @GetMapping("/user/{username}/unfollow")
  public String unfollow(@PathVariable String username) {
    userSessionRepo.unFollow(userProfile);
    userService.saveUser(userSessionRepo);
    return "redirect:/user/{username}";
  }

  private boolean isNotGuest(Authentication authentication) {
    return authentication != null;
  }

  private boolean isLoggedUser(String username, Authentication authentication) {
    String userSessionUsername = getUserSessionUsername(authentication);
    userSessionRepo = (User) userService.loadUserByUsername(userSessionUsername);
    return Objects.equals(username, userSessionUsername);
  }

  private String getUserSessionUsername(Authentication authentication) {
    User userSession = (User) authentication.getPrincipal();
    return userSession.getUsername();
  }

  private boolean isAdmin() {
    return userSessionRepo.isAdmin();
  }

  private boolean isFollowed() {
    return userSessionRepo.getFollowing().contains(userProfile);
  }

}
