package net.daw.alist.controllers;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;



import net.daw.alist.services.TopicHandlerService;
import net.daw.alist.services.PostItemService;

import net.daw.alist.services.ItemField;

import net.daw.alist.models.PostItem;
import net.daw.alist.models.User;
import net.daw.alist.models.Post;
import net.daw.alist.models.Topic;
import net.daw.alist.repositories.UserRepository;
import net.daw.alist.repositories.TopicRepository;
import net.daw.alist.repositories.PostItemRepository;
import net.daw.alist.repositories.PostRepository;

@Controller
public class CreateListController {

  @Autowired
  PostItemRepository item;
  @Autowired
  PostRepository post;
  @Autowired
  TopicRepository topic;
  @Autowired
  TopicHandlerService handler;
  @Autowired
  PostItemService itemService;

  @Autowired
  UserRepository user;

  User author;

  @GetMapping("/create-list")
  public String createList(Model model, Authentication auth) {
    this.author = (User) auth.getPrincipal();
    List<String> topicOptions = handler.topicGetter();
    model.addAttribute("topicOptions", topicOptions);
    
    return "create-list";
  }

  @PostMapping("/create")
  public String saveData(
      @RequestParam String topTitle, @RequestParam List<String> topicList,
      @RequestParam String formDescriptionOne, @RequestParam MultipartFile formFileOne,
      @RequestParam String formDescriptionTwo, @RequestParam MultipartFile formFileTwo,
      @RequestParam String formDescriptionThree,
      @RequestParam MultipartFile formFileThree,
      @RequestParam String formDescriptionFour,
      @RequestParam MultipartFile formFileFour,
      @RequestParam String formDescriptionFive,
      @RequestParam MultipartFile formFileFive,
      Model model) throws IOException {

    List<PostItem> postList = new ArrayList<>();
    System.out.println(topicList);
    ArrayList<ItemField> itemList = new ArrayList<>();
    itemList.add(new ItemField(formDescriptionOne, formFileOne));
    itemList.add(new ItemField(formDescriptionTwo, formFileTwo));
    itemList.add(new ItemField(formDescriptionThree, formFileThree));
    itemList.add(new ItemField(formDescriptionFour, formFileFour));
    itemList.add(new ItemField(formDescriptionFive, formFileFive));

    postList = itemService.itemList(itemList);
    List<Topic> topicLists = new ArrayList<Topic>();
    topicLists = handler.topicList(topicList);
    

    post.save(new Post(author, (String) topTitle, topicLists, postList));
    model.addAttribute("correct", true);
    model.addAttribute("messages", "The top is on the system" );

    return "create-list";
  }

}