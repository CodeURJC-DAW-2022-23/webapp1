package net.daw.alist.controllers;

import net.daw.alist.models.Topic;
import net.daw.alist.models.User;
import net.daw.alist.repositories.TopicRepository;
import net.daw.alist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
  private UserRepository userRepository;
  @Autowired
  private TopicRepository topicRepository;

  @GetMapping("/admin-panel")
  public String adminPanel(Model model) {
    List<User> userList = userRepository.findAll();
    User admin = userRepository.findByUsername("admin").orElseThrow();
    userList.remove(admin);
    model.addAttribute("users", userList);

    List<Topic> topicList = topicRepository.findAll();
    model.addAttribute("topics", topicList);
    return "admin-panel";
  }

  @GetMapping("/admin-panel/delete/{id}")
  public String deleteFromTopic(Model model, @PathVariable long id) {
    Topic topic = topicRepository.findById(id).orElseThrow();
    topicRepository.delete(topic);
    return "redirect:/admin-panel";
  }


  @RequestMapping("/addTopic")
    public String greeting(Model model, @RequestParam String topicName) {
    Topic topic = new Topic(topicName, "");
    topicRepository.save(topic);
    return "redirect:/admin-panel";
}

  @GetMapping("/admin-panel/lock/{id}")
  public String changeLockUser(Model model, @PathVariable long id) {
    User user = userRepository.findById(id).orElseThrow();
    if (user.isLocked()) {
      userRepository.unbanUser(user.getUsername());
    } else {
      userRepository.banUser(user.getUsername());
    }
    
    return "redirect:/admin-panel";
  }
  
}
