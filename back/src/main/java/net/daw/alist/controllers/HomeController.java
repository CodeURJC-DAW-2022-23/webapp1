package net.daw.alist.controllers;

import net.daw.alist.models.Post;
import net.daw.alist.repositories.PostRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;

@Controller
public class HomeController {

  @Autowired
  private PostRepository postRepository;

 //private Map<String, Page<Post>> map = new HashMap<>();

  @GetMapping("/{page}")
  public String home(Model model, @PathVariable int page) {

    Page<Post> posts = postRepository.findAll(PageRequest.of(page, 2));
    model.addAttribute("posts", posts);
/*     Page<Post> newPage = getNextPage(0);
    map.put("posts", newPage);
    model.mergeAttributes(map); */
    
    return "home";
  }

/*   private Page<Post> getNextPage(int page) {
    return postRepository.findAll(PageRequest.of(page, 2));
  } */




}
