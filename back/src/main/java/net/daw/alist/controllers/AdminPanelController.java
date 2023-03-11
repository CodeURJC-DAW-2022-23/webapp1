package net.daw.alist.controllers;

import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {

  @Autowired
  private UserService userService;
  @Autowired
  private PostService postService;

  @GetMapping("/admin-panel")
  public String adminPanel(Model model) {
    Utils utils  = new Utils(userService, postService);
    utils.searchBarInitializer(model);
    return "admin-panel";
  }

}
