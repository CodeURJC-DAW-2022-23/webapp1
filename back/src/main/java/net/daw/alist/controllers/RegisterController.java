package net.daw.alist.controllers;

import lombok.AllArgsConstructor;
import net.daw.alist.security.RegistrationRequest;
import net.daw.alist.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
  public String registerPost(Model model, RegistrationRequest request) {
    String result = registrationService.register(request);
    if (result.equals("Success")){
      model.addAttribute("notVerified", true);
      model.addAttribute("verifyMSG", "Please check your inbox to verify your email address");
      return "/register";
    } else{
      model.addAttribute("error", true);
      model.addAttribute("errorMSG", result);
      return "/register";
    }
  }

  @GetMapping("/registration/confirm")
  public String confirm(@RequestParam("token") String token, HttpServletResponse httpResponse) throws IOException {
    registrationService.confirmToken(token);
    httpResponse.sendRedirect("/sign-in");
    return "/sign-in";
  }

}
