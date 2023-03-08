package net.daw.alist.controllers;

import net.daw.alist.models.Post;

import net.daw.alist.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@Controller
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping({"/","/followed-users/"})
  public String home(Model model) {
    return "feed";
  }
  
  @GetMapping("/posts")
  public String getNewPosts(Model model, @RequestParam int page) {

    Page<Post> newPage = postService.getPosts(page);

    model.addAttribute("posts", newPage);

    return "post";
  }
  
  @GetMapping("/followed-users/posts")
  public String getFollowedUsersPosts(Model model, @RequestParam int page) {

    Page<Post> starredPost = postService.getStarredPosts(page, 1);
    
    model.addAttribute("posts", starredPost);

    return "post";
  }


}
