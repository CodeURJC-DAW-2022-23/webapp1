package net.daw.alist.controllers;

import lombok.AllArgsConstructor;
import net.daw.alist.security.RegistrationRequest;
import net.daw.alist.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
  public String register(RegistrationRequest request) {
    System.out.println(request.getUsername() + request.getPassword() + request.getUsername());
    registrationService.register(request);
    return "/sign-in";
  }

}
