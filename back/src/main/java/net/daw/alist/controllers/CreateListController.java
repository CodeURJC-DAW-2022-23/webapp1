package net.daw.alist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateListController {

  @GetMapping("/create-list")
  public String createList() {
    return "create-list";
  }

}
