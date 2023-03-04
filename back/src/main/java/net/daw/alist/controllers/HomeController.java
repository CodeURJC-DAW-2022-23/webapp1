package net.daw.alist.controllers;

import net.daw.alist.models.Post;
import net.daw.alist.repositories.PostRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @Autowired
  PostRepository postRepository;

  @GetMapping("/")
  public String home(Model model) {
    List<Post> posts = postRepository.findAll();
    model.addAttribute("posts", posts);
    return "home";
  }




}
