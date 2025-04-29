package com.crafter.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class WelcomeController {
	
	@GetMapping(value="")
	public String welcome() {
		return "Welcome to spring security...";
	}

}
