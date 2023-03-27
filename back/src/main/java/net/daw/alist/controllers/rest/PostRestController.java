package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.Objects;
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

  @Operation(summary = "Create new post")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Post created", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
          }),
          @ApiResponse(responseCode = "403", description = "Can't create post if not registered", content = @Content)
  })
  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(Authentication auth, Post post) {
    if (auth != null) {
      postService.save(post);
      return post;
    }
    return null;
  }

  @Operation(summary = "Get specific post")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Post found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
          }),
          @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
  })
  @GetMapping("/{postId}")
  public ResponseEntity<Post> getPost(@PathVariable long postId) {
    Optional<Post> optionalPost = postService.findByID(postId);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      return new ResponseEntity<>(post, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Delete specific post")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Post deleted", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
          }),
          @ApiResponse(responseCode = "403", description = "Can't delete other one post", content = @Content),
          @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
  })
  @DeleteMapping("/{postId}")
  public ResponseEntity<Post> deletePost(Authentication auth, @PathVariable long postId) {
    Optional<Post> optionalPost = postService.findByID(postId);
    if (auth != null && optionalPost.isPresent()) {
      User userSession = (User) userService.loadUserByUsername(((User) auth.getPrincipal()).getUsername());
      Post post = optionalPost.get();
      if (Objects.equals(userSession.getId(), post.getAuthor().getId())) {
        postService.delete(post);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Current user upvote a post")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User found and upvoted the post", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
          }),
          @ApiResponse(responseCode = "403", description = "Current user not logged in", content = @Content),
          @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
  })
  @PutMapping("/{postId}/upvotes")
  public ResponseEntity<Post> upvotePost(Authentication authentication, @PathVariable long postId) {
    Optional<Post> optionalPost = postService.findByID(postId);
    if(optionalPost.isPresent()) {
      User userSession = (User) userService.loadUserByUsername(((User) authentication
              .getPrincipal())
              .getUsername());
      votesService.actionUpVote(postId, userSession);
      return new ResponseEntity<>(optionalPost.get(), HttpStatus.OK);
    }
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }

  @Operation(summary = "Current user downvote a post")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User found and downvoted the post", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
          }),
          @ApiResponse(responseCode = "403", description = "Current user not logged in", content = @Content),
          @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
  })
  @PutMapping("/{postId}/downvotes")
  public ResponseEntity<Post> downvotePost(Authentication authentication, @PathVariable long postId) {
    Optional<Post> optionalPost = postService.findByID(postId);
    if(optionalPost.isPresent()) {
      User userSession = (User) userService.loadUserByUsername(((User) authentication
              .getPrincipal())
              .getUsername());
      votesService.actionDownVote(postId, userSession);
      return new ResponseEntity<>(optionalPost.get(), HttpStatus.OK);
    }
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
