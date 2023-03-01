package net.daw.alist.controllers;

import java.io.IOException;
import java.net.URI;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.hibernate.engine.jdbc.BlobProxy;

import net.daw.alist.services.TopicHandler;
import net.daw.alist.models.PostItem;
import net.daw.alist.models.Post;
import net.daw.alist.models.Topic;
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

  TopicHandler handler;

  @GetMapping("/create-list")
  public String createList() {

    return "create-list";
  }

  @PostMapping("/create")
  public String saveData(
      @RequestParam String topTitle, @RequestParam String topicList,
      @RequestParam String formDescriptionOne, @RequestParam MultipartFile formFileOne,
      @RequestParam String formDescriptionTwo, @RequestParam MultipartFile formFileTwo,
      @RequestParam(required = false) String formDescriptionThree,
      @RequestParam(required = false) MultipartFile formFileThree,
      @RequestParam(required = false) String formDescriptionFour,
      @RequestParam(required = false) MultipartFile formFileFour,
      @RequestParam(required = false) String formDescriptionFive,
      @RequestParam(required = false) MultipartFile formFileFive) throws IOException {

    List<PostItem> postList = new ArrayList<>();

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

    /* Optimize it with a loop when posbile */
    PostItem item1 = new PostItem((String) formDescriptionOne,
        (BlobProxy.generateProxy(formFileOne.getInputStream(), formFileOne.getSize())),
        (String) location.toString() + "1");
    postList.add(item1);

    PostItem item2 = new PostItem((String) formDescriptionTwo,
        (BlobProxy.generateProxy(formFileTwo.getInputStream(), formFileTwo.getSize())),
        (String) location.toString() + "2");
    postList.add(item2);

    if (formDescriptionThree.equals(null)) {
      PostItem item3 = new PostItem((String) formDescriptionThree,
          (BlobProxy.generateProxy(formFileThree.getInputStream(), formFileThree.getSize())),
          (String) location.toString() + "3");
      postList.add(item3);
    }

    if (formDescriptionFour.equals(null)) {
      PostItem item4 = new PostItem((String) formDescriptionFour,
          (BlobProxy.generateProxy(formFileFour.getInputStream(), formFileFour.getSize())),
          (String) location.toString() + "4");
      postList.add(item4);
    }

    if (formDescriptionFive.equals(null)) {
      PostItem item5 = new PostItem((String) formDescriptionFive,
          (BlobProxy.generateProxy(formFileFive.getInputStream(), formFileFive.getSize())),
          (String) location.toString() + "5");
      postList.add(item5);
    }

    Topic top = new Topic();
    if (handler.topicChecker(topicList)) {
      top.setName(topicList);
    } 
    else {
      top.setName("Random");
    }

    post.save(new Post(topTitle, postList, top));

    return "home";
  }

}