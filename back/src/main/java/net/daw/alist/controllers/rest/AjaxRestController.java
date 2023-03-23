package net.daw.alist.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping("/api/ajax")
public class AjaxRestController {
  
  @Autowired
  PostService postService;

  @Operation(summary = "Load more posts")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "New posts loaded", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))}),
          @ApiResponse(responseCode = "404", description = "No new posts found", content = @Content)
  })
  @GetMapping("/posts")
  public Page<Post> getNewPosts(@RequestParam int page) {
    if (page <= (int) Math.ceil(postService.count() / 2))
      return postService.getPosts(page);
    return null;
  }

  @Operation(summary = "Load more posts from followed-users")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "New posts loaded", content = {
                  @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))}),
          @ApiResponse(responseCode = "404", description = "No new posts found", content = @Content)
  })
  @GetMapping("/followed-users/posts")
  public Page<Post> getFollowedUsersPosts(Authentication authentication, @RequestParam int page) {
    User currentUser = (User) authentication.getPrincipal();

    if (page <= (int) Math.ceil(postService.count()/2))
      return postService.getStarredPosts(page, currentUser.getId().intValue());
      
    return null;
  }
}
