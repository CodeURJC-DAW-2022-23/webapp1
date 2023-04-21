package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.daw.alist.models.Post;
import net.daw.alist.models.PostItem;
import net.daw.alist.models.Topic;
import net.daw.alist.models.User;
import net.daw.alist.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.daw.alist.utils.Utils.pathToImage;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

  @Autowired
  private PostService postService;

  @Autowired
  private PostItemService postItemService;

  @Autowired
  private TopicService topicService;

  @Autowired
  private UserService userService;

  @Autowired
  private VotesService votesService;


  @Operation(summary = "Create new post")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Post created", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
    }),
    @ApiResponse(responseCode = "403", description = "Can't create post if not registered", content = @Content),
    @ApiResponse(responseCode = "400", description = "Bad formatting", content = @Content)
  })
  @PostMapping("/")
  public ResponseEntity<Post> createPost(Authentication auth, @RequestBody Data content) throws SQLException, IOException {
    if (content.getTitle() == null || content.getTopicStrings() == null || content.getItems() == null)
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    User author = (User) auth.getPrincipal();
    author = userService.findByID(author.getId()).orElseThrow();
    List<PostItem> items = content.getItems();
    for (PostItem item: items) {
      postItemService.save(item);
    }
    List<Topic> topicList = topicService.getTopics(content.getTopicStrings());
    Post post = new Post(author, content.getTitle(), topicList, items);
    postService.save(post);
    return new ResponseEntity<>(post, HttpStatus.CREATED);
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
    if (optionalPost.isPresent()) {
      User userSession = (User) userService.loadUserByUsername(((User) authentication
              .getPrincipal())
              .getUsername());
      votesService.actionDownVote(postId, userSession);
      return new ResponseEntity<>(optionalPost.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @AllArgsConstructor
  @Getter
  @Setter
  @EqualsAndHashCode
  private static class Data {
    private final String title;
    private final List<String> topicStrings;
    private final List<PostItem> items;
  }

}
