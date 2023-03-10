package net.daw.alist.controllers;

import net.daw.alist.models.Comment;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
public class TopListController {
  @Autowired
  private UserService userService;
  @Autowired
  private PostService postService;

  private User userProfile;
  private Post post;

  @GetMapping("/top-list/{id}")
  public String topList(Model model, Authentication authentication, @PathVariable long id){
    Utils utils  = new Utils(userService, postService);
    utils.searchBarInitializer(model);

    userProfile = (User) authentication.getPrincipal();
    if(!authentication.isAuthenticated()){
      model.addAttribute("isRegistered", false);
    } else{
      model.addAttribute("isRegistered", true);
      model.addAttribute("loggedUserImagePath", userProfile.getImagePath());
    }

    post = postService.findByID(id).orElseThrow();

    model.addAttribute("id", id);
    model.addAttribute("posts", post);
    model.addAttribute("title", post.getTitle());

    List<Comment> comments = post.getComments();
    model.addAttribute("comments", comments);

    return "top-list";
  }

  @RequestMapping("/top-list/{id}/addComment")
  public String addComment(Model model, @RequestParam String commentContent) throws SQLException, IOException {
    User author = userService.findByID(userProfile.getId()).orElseThrow();
    Comment comment = new Comment(author, commentContent, null);
    post.addComment(comment);
    postService.save(post);
    return "redirect:/top-list/{id}";
  }

}
