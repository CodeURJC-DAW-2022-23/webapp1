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
public class HomeController {

  @Autowired
  private PostService postService;

  @GetMapping("/")
  public String home(Model model) {
    Page<Post> posts = postService.getPosts(0, 2);
    model.addAttribute("posts", posts);
    return "home";
  }
  
  @GetMapping("/posts")
  public String getNewPosts(Model model, @RequestParam int page) {

    Page<Post> newPage = postService.getPosts(page, 1);

    model.addAttribute("posts", newPage);
  
    return "post";
  }


}
