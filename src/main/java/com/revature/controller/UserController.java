package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.template.LoginTemplate;
import com.revature.template.MessageTemplate;

import jakarta.validation.Valid;

@Controller
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request; 
	@Autowired
	private HttpServletResponse response;
	
	@PostMapping(path = "login")
	public ResponseEntity login(@RequestBody @Valid LoginTemplate loginTemplate) {
		try {
			User user = userService.login(loginTemplate.getUsername(), loginTemplate.getPassword()); 
			
			HttpSession session = request.getSession(true);
			session.setAttribute("loggedInUser", user);
			
			return ResponseEntity.status(200).body(new MessageTemplate("Now logged in as " + loginTemplate.getUsername()));
			
		} catch (UserNotFoundException e) {
			logger.info("Could not send request successfully. Please try again!");
			
			return ResponseEntity.status(400).body(new MessageTemplate("Access Denied -> Could not find user with provided username and password: " + loginTemplate.getUsername() + "~~" + loginTemplate.getPassword())); 
		}
	}
	
	@PostMapping(path = "logout")
	public ResponseEntity logout() {
		try {			
			HttpSession session = request.getSession(true);
			System.out.println(session.getAttribute("loggedInUser"));
			session.setAttribute("loggedInUser", null);		
			System.out.println(session.getAttribute("loggedInUser"));			
			return ResponseEntity.status(200).build();
			
		} catch (Exception e) {
			logger.info("Could not send request successfully. Exception: " + e);
			
			return ResponseEntity.status(401).body((Object) new MessageTemplate("Could not log user out. Unknown error occured.")); 
		}
	}

}
