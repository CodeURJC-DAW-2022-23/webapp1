package net.daw.alist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopListController {

  @GetMapping("/top-list")
  public String topList() {
    return "top-list";
  }

}
