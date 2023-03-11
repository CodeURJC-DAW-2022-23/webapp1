package net.daw.alist.controllers;

import jdk.jshell.execution.Util;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
public class PostController {
  @Autowired
  private UserService userService;
  @Autowired
  private PostService postService;

  @GetMapping({"/","/followed-users/"})
  public String home(Model model) {
    Utils utils  = new Utils(userService, postService);
    utils.searchBarInitializer(model);
    return "feed";
  }

  @GetMapping("/posts")
  public String getNewPosts(Model model, @RequestParam int page) {

    Page<Post> newPage = postService.getPosts(page);

    model.addAttribute("posts", newPage);

    return "post";
  }
  
  @GetMapping("/followed-users/posts")
  public String getFollowedUsersPosts(Model model, Authentication authentication, @RequestParam int page) {

    User currentUser = (User) authentication.getPrincipal();

    Page<Post> starredPost = postService.getStarredPosts(page,currentUser.getId().intValue());
    
    model.addAttribute("posts", starredPost);

    return "post";
  }


}
