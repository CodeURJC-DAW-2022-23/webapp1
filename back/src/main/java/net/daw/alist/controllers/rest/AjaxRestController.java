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

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class AjaxRestController {
  
  @Autowired
  PostService postService;

  @Operation(summary = "Load more posts")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "New posts loaded", content = {
      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))
    }),
    @ApiResponse(responseCode = "404", description = "No new posts found", content = @Content)
  })
  @GetMapping("/")
  public Page<Post> getNewPosts(Authentication authentication, @RequestParam int page, @RequestParam Optional<Boolean> filter) {
    boolean validPage = page <= (int) Math.ceil(postService.count()/2);
    boolean filterPosts = false;
    if (filter.isPresent()) {
      filterPosts = filter.get();
    }
    if ((authentication != null) && filterPosts && validPage) {
      User currentUser = (User) authentication.getPrincipal();
      return postService.getStarredPosts(page, currentUser.getId().intValue());
    } else if (validPage) return postService.getPosts(page);
    return null;
  }

}
