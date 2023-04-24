package net.daw.alist.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.daw.alist.models.User;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

  @Autowired
  private UserService userService;

  @Autowired
  private Utils utils;

  @Operation(summary = "Get a user by its username")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    }),
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  @GetMapping("/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    return getUserResponseEntity(username, userService);
  }

  public static ResponseEntity<User> getUserResponseEntity(String username, UserService userService) {
    Optional<User> userPrincipal = userService.findByUsername(username);
    if (userPrincipal.isPresent()) {
      User user = userPrincipal.get();
      user.setFollowingCount(user.getFollowing().size());
      user.setFollowersCount(user.getFollowers().size());
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }
  @GetMapping("/names/{prefix}")
  public ResponseEntity<List<User>> getUserByPrefix(@PathVariable String prefix){
    List<User> firstElementsList = userService.findUserPrefix(prefix).stream().limit(5).collect(Collectors.toList());

    return new ResponseEntity<>(firstElementsList, HttpStatus.OK);
  }

  @Operation(summary = "Edit user by id")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found and banned/unbanned state changed", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    }),
    @ApiResponse(responseCode = "403", description = "Admin access required", content = @Content),
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
    @ApiResponse(responseCode = "400", description = "Operation doesn't exist", content = @Content)
  })
  @PutMapping ("/{userId}")
  public ResponseEntity<User> userOperation(
    Authentication authentication,
    @PathVariable Long userId,
    @RequestParam String operation
  ) {
    Optional<User> optionalUser = userService.findByID(userId);
    if (optionalUser.isEmpty())
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    String userRole = utils.getCurrentUserRole(authentication);

    User user = optionalUser.get();
    switch (operation) {
      case("ban"):
        if (!userRole.equals("ADMIN"))
          return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (user.isLocked()) {
          userService.unbanUser(user.getUsername());
          user.setLocked(false);
        } else {
          userService.banUser(user.getUsername());
          user.setLocked(true);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
      //case("profile"):...
      //Add more operations here
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @Operation(summary = "Current user follows another user by its username")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found and followed/unfollowed state changed", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    }),
    @ApiResponse(responseCode = "403", description = "Current user not logged in", content = @Content),
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  @PutMapping("/{username}/follow")
  public ResponseEntity<User> follow(Authentication authentication, @PathVariable String username) {
    Optional<User> optionalUser = userService.findByUsername(username);
    User userSession = (User) authentication.getPrincipal();
    userSession = userService.findByID(userSession.getId()).orElseThrow();
    if (optionalUser.isPresent()) {
      User userProfile = optionalUser.get();
      if (userProfile.getFollowers().contains(userSession))
        userSession.unFollow(userProfile);
      else
        userSession.follow(userProfile);
      userService.saveUser(userSession);
      return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @Operation(summary = "Profile user following")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Follow.class))
    }),
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
    @ApiResponse(responseCode = "400", description = "User logged in but not found", content = @Content)
  })
  @GetMapping("{username}/following")
  public ResponseEntity<Follow> following(HttpServletRequest request, @PathVariable String username) {
    return checkFollowingFollow(request, username, true);
  }

  @Operation(summary = "Profile user followers")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "User found", content = {
      @Content(mediaType = "application/json", schema = @Schema(implementation = Follow.class))
    }),
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
    @ApiResponse(responseCode = "400", description = "User logged in but not found", content = @Content)
  })
  @GetMapping("{username}/followers")
  public ResponseEntity<Follow> followers(HttpServletRequest request, @PathVariable String username) {
    return checkFollowingFollow(request, username, false);
  }

  private ResponseEntity<Follow> checkFollowingFollow(
    HttpServletRequest request,
    String username,
    boolean following
  ) {
    Optional<User> optionalUserProfile =  userService.findByUsername(username);
    if (optionalUserProfile.isPresent()) {
      User userProfile = optionalUserProfile.get();
      List<User> followUsers;
      if (following) followUsers = userProfile.getFollowing();
      else followUsers = userProfile.getFollowers();
      List<FollowUser> followUserFollows = new ArrayList<>();
      boolean userLoggedIn = request.getUserPrincipal() != null;
      if (userLoggedIn) {
        String userSessionUsername = request.getUserPrincipal().getName();
        Optional<User> optionalUserSession = userService.findByUsername(userSessionUsername);
        if (optionalUserSession.isPresent()) {
          User userSession = optionalUserSession.get();
          for (User user: followUsers) {
            FollowUser followUser = new FollowUser(user.getUsername(), user.getId(), userSession.getFollowing().contains(user));
            followUserFollows.add(followUser);
          }
        } else new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {
        for (User user: followUsers) {
          FollowUser followUser = new FollowUser(user.getUsername(), user.getId(), false);
          followUserFollows.add(followUser);
        }
      }
      Follow follow = new Follow(followUsers.size(), followUserFollows);
      return new ResponseEntity<>(follow, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  private static class Follow {
    private final int count;
    private final List<FollowUser> users;
  }

  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  private static class FollowUser {
    private final String username;
    private final Long id;
    private final boolean follow;
  }

}
