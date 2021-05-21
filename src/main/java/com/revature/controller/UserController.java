package com.revature.controller;

import java.sql.SQLException;

import javax.persistence.PersistenceException;
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

import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.template.LoginTemplate;
import com.revature.template.MessageTemplate;
import com.revature.template.RegisterTemplate;

import jakarta.validation.Valid;

@Controller
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;
	@SuppressWarnings("unused")
	@Autowired
	private HttpServletResponse response;
	
	@PostMapping(path = "login")
	public ResponseEntity<Object> login(@RequestBody @Valid LoginTemplate loginTemplate) {
		try {
			User user = userService.login(loginTemplate.getUsername(), loginTemplate.getPassword());
			if(user == null) {
				throw new NullPointerException();
			}

			HttpSession session = request.getSession(true);
			session.setAttribute("loggedInUser", user);

			return ResponseEntity.status(200)
					.body(new MessageTemplate("Now logged in as " + loginTemplate.getUsername()));

		} catch (UserNotFoundException e) {
			logger.info("Could not send request successfully. Please try again!");

			return ResponseEntity.status(400).body(
					new MessageTemplate("Access Denied -> Could not find user with provided username and password: "
							+ loginTemplate.getUsername() + "~~" + loginTemplate.getPassword()));
		} catch (NullPointerException e1) {
			logger.info("Could not send request successfully. Please try again!");
			return ResponseEntity.status(400).body(
					new MessageTemplate("Access Denied -> Please make sure username and password are not blank. User provided: "
							+ loginTemplate.getUsername() + "~~" + loginTemplate.getPassword()));
		}
	}

	@PostMapping(path = "logout")
	public ResponseEntity<Object> logout() {
		try {
			HttpSession session = request.getSession(true);

			Object user = session.getAttribute("loggedInUser");
			session.setAttribute("loggedInUser", null);

			if (user == null) {
				return ResponseEntity.status(401)
						.body((Object) new MessageTemplate("Could not log user out. No user was logged in."));
			}
			
			return ResponseEntity.status(200)
					.body((Object) new MessageTemplate("Successfully logged out user: " + user));

		} catch (Exception e) {
			logger.info("Could not send request successfully. Exception: " + e);

			return ResponseEntity.status(401)
					.body((Object) new MessageTemplate("Could not log user out. Unknown error occured."));
		}
	}

	@PostMapping(path = "addUser")
	public ResponseEntity<Object> addUser(@RequestBody RegisterTemplate registerTemplate) throws BadParameterException, SQLException {
		try {	
			User user = (User) userService.registerUser(registerTemplate);
			HttpSession session = request.getSession(true);
			session.setAttribute("loggedInUser", user);

			return ResponseEntity.status(200)
					.body((Object)new MessageTemplate("Now logged in as " + user.getUsername()));
	
		} catch(PersistenceException e) {
			logger.warn("Could not send request successfully. Please try again!");
			return ResponseEntity.status(400).body(
					new MessageTemplate("Could not register new user. User with that username or email already exists!")); 
		} catch (NullPointerException e1) {
			logger.warn("Could not send request successfully. Please try again!");
			return ResponseEntity.status(400).body(
					new MessageTemplate("Could not register new user. Make sure all fields are filled in correctly.")); 
		}
	}

}
