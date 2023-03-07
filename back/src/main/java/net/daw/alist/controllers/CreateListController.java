package net.daw.alist.controllers;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import net.daw.alist.services.PostService;
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
  PostService postService;

  @GetMapping("/create-list")
  public String createList(Model model) {
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
    @RequestParam MultipartFile formFileOne,
    @RequestParam String formDescriptionTwo,
    @RequestParam MultipartFile formFileTwo,
    @RequestParam String formDescriptionThree,
    @RequestParam MultipartFile formFileThree,
    @RequestParam String formDescriptionFour,
    @RequestParam MultipartFile formFileFour,
    @RequestParam String formDescriptionFive,
    @RequestParam MultipartFile formFileFive
  ) throws IOException {
    List<ItemField> itemFields = new ArrayList<>();
    itemFields.add(new ItemField(formDescriptionOne, formFileOne));
    itemFields.add(new ItemField(formDescriptionTwo, formFileTwo));
    itemFields.add(new ItemField(formDescriptionThree, formFileThree));
    itemFields.add(new ItemField(formDescriptionFour, formFileFour));
    itemFields.add(new ItemField(formDescriptionFive, formFileFive));

    List<PostItem> postItems = postItemService.getPostItems(itemFields);
    List<Topic> selectedTopics = topicService.getTopics(topicList);
    User author = (User) authentication.getPrincipal();
    postService.save(new Post(author, topTitle, selectedTopics, postItems));

    model.addAttribute("correct", true);
    model.addAttribute("messages", "The top is on the system");
    return "create-list";
  }

}