package net.daw.alist.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

  @GetMapping("/login")
  public ResponseEntity<String> login() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
