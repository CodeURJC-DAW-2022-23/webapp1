package net.daw.alist.controllers;

import net.daw.alist.models.Post;

import net.daw.alist.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;

@Controller
public class HomeController {

  @Autowired
  private PostService postService;

  @GetMapping("/")
  public String home(Model model) {
    Page<Post> posts = postService.getPosts(10);
    model.addAttribute("posts", posts);
    return "home";
  }

}
