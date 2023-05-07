package net.daw.alist.controllers.rest;

import net.daw.alist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;

import java.util.Optional;

@RestController
@RequestMapping("/new/api/posts")
public class AjaxRestController {
  
  @Autowired
  PostService postService;

  @Autowired
  UserService userService;

  @Operation(summary = "Load more posts")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "New posts loaded", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
    }),
    @ApiResponse(responseCode = "404", description = "No new posts found", content = @Content)
  })
  @GetMapping("")
  public ResponseEntity<Page<Post>> getNewPosts(
    Authentication authentication,
    @RequestParam int page,
    @RequestParam Optional<Boolean> filter,
    @RequestParam Optional<String> username
  ) {
    boolean validPage = page < (int) Math.ceil((float) postService.count() / 2);
    if (validPage) {
      boolean filterPosts = false;
      if (filter.isPresent()) filterPosts = filter.get();
      boolean searchUsername = username.isPresent();
      if (searchUsername) {
        Optional<User> optionalUser = userService.findByUsername(username.get());
        if (optionalUser.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        int userId = optionalUser.get().getId().intValue();
        return new ResponseEntity<>(postService.getUserPosts(page, userId), HttpStatus.OK);
      } else if ((authentication != null) && filterPosts) {
        User currentUser = (User) authentication.getPrincipal();
        int currentUserId = currentUser.getId().intValue();
        return new ResponseEntity<>(postService.getStarredPosts(page, currentUserId), HttpStatus.OK);
      } else return new ResponseEntity<>(postService.getPosts(page), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
