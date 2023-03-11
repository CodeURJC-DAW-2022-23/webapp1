package net.daw.alist.controllers;

import net.daw.alist.models.Topic;
import net.daw.alist.models.User;
import net.daw.alist.services.TopicService;
import net.daw.alist.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminPanelController {

  @Autowired
  private UserService userService;
  @Autowired
  private TopicService topicService;
  @Autowired
  private PostService postService;

  @GetMapping("/admin-panel")
  public String adminPanel(Model model) {
    List<User> userList = userService.findAll();
    User admin = userService.findByUsername("admin");
    userList.remove(admin);
    model.addAttribute("users", userList);

    List<Topic> topicList = topicService.findAll();
    model.addAttribute("topics", topicList);
  public String adminPanel(Model model) {
    Utils utils  = new Utils(userService, postService);
    utils.searchBarInitializer(model);
    return "admin-panel";
  }

  @GetMapping("/admin-panel/delete/{id}")
  public String deleteFromTopic(Model model, @PathVariable long id) {
    Topic topic = topicService.findById(id);
    topicService.delete(topic);
    return "redirect:/admin-panel";
  }


  @RequestMapping("/addTopic")
    public String addTopic(Model model, @RequestParam String topicName) {
    Topic topic = new Topic(topicName, "");
    topicService.save(topic);
    return "redirect:/admin-panel";
}

  @GetMapping("/admin-panel/lock/{id}")
  public String changeLockUser(Model model, @PathVariable long id) {
    User user = userService.findById(id);
    if (user.isLocked()) {
      userService.unbanUser(user.getUsername());
    } else {
      userService.banUser(user.getUsername());
    }
    
    return "redirect:/admin-panel";
  }
  
}
