package net.daw.alist.controllers;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import net.daw.alist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import net.daw.alist.services.TopicService;
import net.daw.alist.services.PostItemService;

import net.daw.alist.utils.ItemField;

import net.daw.alist.models.PostItem;
import net.daw.alist.models.User;
import net.daw.alist.models.Post;
import net.daw.alist.models.Topic;

@Controller
public class CreateListController {

  @Autowired
  TopicService topicService;

  @Autowired
  PostItemService postItemService;

  @Autowired
  private UserService userService;

  @Autowired
  PostService postService;

  @GetMapping("/create-list")
  public String createList(Model model) {
    Utils utils  = new Utils(userService, postService);
    utils.searchBarInitializer(model);
    List<String> topicOptions = topicService.getAllTopics();
    model.addAttribute("topicOptions", topicOptions);
    return "create-list";
  }

  @PostMapping("/create-list/save")
  public String saveData(
    Model model,
    Authentication authentication,
    @RequestParam String topTitle,
    @RequestParam List<String> topicList,
    @RequestParam String formDescriptionOne,
    @RequestParam MultipartFile formImageOne,
    @RequestParam String formDescriptionTwo,
    @RequestParam MultipartFile formImageTwo,
    @RequestParam String formDescriptionThree,
    @RequestParam MultipartFile formImageThree,
    @RequestParam String formDescriptionFour,
    @RequestParam MultipartFile formImageFour,
    @RequestParam String formDescriptionFive,
    @RequestParam MultipartFile formImageFive
  ) throws IOException, SQLException {
    List<ItemField> itemFields = new ArrayList<>();
    itemFields.add(new ItemField(formDescriptionOne, formImageOne));
    itemFields.add(new ItemField(formDescriptionTwo, formImageTwo));
    itemFields.add(new ItemField(formDescriptionThree, formImageThree));
    itemFields.add(new ItemField(formDescriptionFour, formImageFour));
    itemFields.add(new ItemField(formDescriptionFive, formImageFive));

    List<PostItem> postItems = postItemService.getPostItems(itemFields);
    List<Topic> selectedTopics = topicService.getTopics(topicList);
    User author = (User) authentication.getPrincipal();
    Post post = new Post(author, topTitle, selectedTopics, postItems);
    postService.save(post);

    model.addAttribute("correct", true);
    model.addAttribute("messages", "The top is on the system");
    return "create-list";
  }

}