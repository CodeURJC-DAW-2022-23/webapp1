package net.daw.alist.controllers;

import net.daw.alist.models.Post;
import net.daw.alist.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PostController {
  
  @Autowired
  private PostService postService;

  
}
