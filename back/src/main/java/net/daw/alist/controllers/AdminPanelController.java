package net.daw.alist.controllers;

import net.daw.alist.models.User;
import net.daw.alist.repositories.TopicRepository;
import net.daw.alist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminPanelController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TopicRepository topicRepository;

  @GetMapping("/admin-panel")
  public String adminPanel(Model model) {
    List<String> usernameList = userRepository.findAllUsernames();
    model.addAttribute("users", usernameList);

    List<String> topicNameList = topicRepository.findAllTopicNames();
    model.addAttribute("topics", topicNameList);
    return "admin-panel";
  }

}
