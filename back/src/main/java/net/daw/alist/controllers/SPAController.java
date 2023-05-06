package net.daw.alist.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class SPAController {
	@GetMapping({"/new/**/{path:[^\\.]*}", "/{path:new[^\\.]*}"})
	public String redirect() {
		return "forward:/new/index.html";
	}
}
