package net.daw.alist.controllers;

import lombok.AllArgsConstructor;
import net.daw.alist.services.RegistrationService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/registration")
@AllArgsConstructor
public class RegisterController {

  private final RegistrationService registrationService;

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @PostMapping
  public String register(@RequestBody RegistrationRequest request) {
    return registrationService.register(request);
  }

}
