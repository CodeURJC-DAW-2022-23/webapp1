package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.daw.alist.models.Comment;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;


  @PostMapping("/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Comment createComment(@RequestBody String content, @PathVariable long postId, Authentication auth) throws SQLException, IOException {
    User user = (User) auth.getPrincipal();
    Comment comment = new Comment(user, content, null);
    Optional<Post> optionalPost = postService.findByID(postId);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      post.addComment(comment);
      postService.save(post);
      return comment;
    }
    return null;
  }

  @Operation(summary = "Get all the comments of a post")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Post found", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class)))}),
          @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
  })
  @GetMapping("/post/{postId}")
  public ResponseEntity<List<Comment>> getPostComments(@PathVariable long postId) {
    Optional<Post> optionalPost = postService.findByID(postId);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      List<Comment> comments = post.getComments();
      return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Get all the comments of an user")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User found", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class)))}),
          @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Comment>> getUserComments(@PathVariable long userId) {
    Optional<User> optionalUser = userService.findByID(userId);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      List<Comment> comments = user.getComments();
      return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Delete a comment of a post")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Comment deleted", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class)))}),
          @ApiResponse(responseCode = "403", description = "Can´t delete other one comment", content = @Content),
          @ApiResponse(responseCode = "404", description = "Post or comment not found", content = @Content)
  })
  @DeleteMapping("/{postId}/{commentId}")
  public ResponseEntity<Post> deletePost(@PathVariable long postId, @PathVariable long commentId, Authentication auth) {
    Optional<Post> optionalPost = postService.findByID(postId);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      User user = (User) auth.getPrincipal();
      Optional<Comment> optionalComment = postService.getCommentByID(post, commentId);
      if (optionalComment.isPresent()) {
        Comment comment = optionalComment.get();
        if (user.getId().equals(comment.getAuthor().getId()) || user.isAdmin()) {
          post.getComments().remove(comment);
          postService.save(post);
          return new ResponseEntity<>(HttpStatus.OK);
        } else {
          return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
