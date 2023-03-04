package net.daw.alist.controllers;

import net.daw.alist.models.User;
import net.daw.alist.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ProfileController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/user/{username}")
  public String profile(Model model, HttpServletResponse httpResponse, @PathVariable String username) throws IOException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      model.addAttribute("user", user.get());
      return "profile";
    }
    httpResponse.sendRedirect("/error");
    return "error";
  }

}
