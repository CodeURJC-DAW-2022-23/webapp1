package net.daw.alist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SignInController {

  @GetMapping("/sign-in")
  public String login(Model model, @RequestParam Optional<String> error) {
    error.ifPresent(e -> model.addAttribute("error", true));
    return "sign-in";
  }

}
