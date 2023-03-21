package net.daw.alist.controllers.rest;

import jdk.jshell.execution.Util;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

  @Autowired
  private UserService userService;

  @Autowired
  private Utils utils;

  @GetMapping("/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    Optional<User> optionalUser = userService.findByUsername(username);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping ("/{id}")
  public ResponseEntity<User> banUser(Authentication authentication, @PathVariable Long id) {
    String userRole = utils.getCurrentUserRole(authentication);
    if (!userRole.equals("ADMIN"))
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    Optional<User> optionalUser = userService.findByID(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      if (user.isLocked()) {
        userService.unbanUser(user.getUsername());
      } else {
        userService.banUser(user.getUsername());
      }
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  //TODO: followers
  @PutMapping("/{username}/follow")
  public ResponseEntity<User> follow(Authentication authentication, @PathVariable String username){
    User userProfile = (User) userService.loadUserByUsername(username);
    User userSession = (User) authentication;
    userSession.follow(userProfile);
    userService.saveUser(userSession);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{username}/unfollow")
  public ResponseEntity<User> unfollow(Authentication authentication, @PathVariable String username){
    User userProfile = (User) userService.loadUserByUsername(username);
    User userSession = (User) authentication;
    userSession.unFollow(userProfile);
    userService.saveUser(userSession);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
