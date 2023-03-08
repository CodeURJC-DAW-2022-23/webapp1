package net.daw.alist.controllers;

import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
  @Autowired
  private UserService userService;

  @Autowired
  private PostService postService;

  @GetMapping("/")
  public String home(Model model) {
    searchBarInitializer("/", model);
    return "home";
  }
  public void searchBarInitializer(String redirectURL, Model model){
    List<User> userList = userService.findAll();
    model.addAttribute("searchSuggestedUsers", userList);
    List<Post> postList = postService.findAll();
    model.addAttribute("searchSuggestedPosts", postList);
  }
}
