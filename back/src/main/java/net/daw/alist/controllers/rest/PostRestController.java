package net.daw.alist.controllers.rest;

import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import net.daw.alist.services.VotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @Autowired
  private VotesService votesService;
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(Post post) {
    postService.save(post);
    return post;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPost(@PathVariable long id) {
    Optional<Post> optionalPost = postService.findByID(id);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      return new ResponseEntity<>(post, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Post> deletePost(@PathVariable long id) {
    Optional<Post> optionalPost = postService.findByID(id);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      postService.delete(post);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/{id}/upvote")
  public ResponseEntity<Post> upvoteAction(Authentication authentication, @PathVariable long id) {
    Optional<Post> optionalPost = postService.findByID(id);
    if(!(authentication == null) && optionalPost.isPresent() ) {
      User userSession = (User) userService.loadUserByUsername(((User) authentication
              .getPrincipal())
              .getUsername());
      votesService.actionUpVote(id, userSession);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else if (authentication==null)
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }

  @PutMapping("/{id}/downvote")
  public ResponseEntity<Post> downvoteAction(Authentication authentication, @PathVariable long id) {
    Optional<Post> optionalPost = postService.findByID(id);
    if(!(authentication == null) && optionalPost.isPresent() ) {
      User userSession = (User) userService.loadUserByUsername(((User) authentication
              .getPrincipal())
              .getUsername());
      votesService.actionDownVote(id, userSession);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else if (authentication==null)
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }


}
