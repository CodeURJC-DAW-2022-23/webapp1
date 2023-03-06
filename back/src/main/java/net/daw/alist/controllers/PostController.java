package net.daw.alist.controllers;

import net.daw.alist.models.Post;
import net.daw.alist.services.PostService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class PostController {
  
  @Autowired
  private PostService postService;

  @GetMapping("/posts")
  @ResponseBody
  public Page<Post> getNewPosts(@RequestParam int page) {

    Page<Post> newPage = postService.getPosts(page, 1);
  

    return newPage;
    /* if (newPage != null) {
      return ResponseEntity.ok(newPage);
    } else {
      return ResponseEntity.notFound().build();
    }
     */
  }

}
