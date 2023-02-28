package net.daw.alist.controllers;

import lombok.AllArgsConstructor;
import net.daw.alist.security.RegistrationRequest;
import net.daw.alist.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class RegisterController {
  @Autowired
  private final RegistrationService registrationService;

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @PostMapping("/register")
  public String register(Model model, RegistrationRequest request) {
    String result = registrationService.register(request);
    if (result.equals("Success")){
      return "/sign-in";
    } else{
      model.addAttribute("error", true);
      model.addAttribute("errorMSG", result);
      return "/register";
    }
  }

}
