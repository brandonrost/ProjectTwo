package com.revature.service;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dao.UserDAO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

@Service
public class UserService {
	
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDAO userDAO; 
	
	@Transactional(rollbackFor = {UserNotFoundException.class})
	public User login(String username, String password) throws UserNotFoundException {
		try {
			User user = userDAO.getUserByUsernameAndPassword(username, password); 
			logger.info("Successfully completed business logic inside of the " + getClass() + " class.");
			return user; 
		} catch (NoResultException e) {
			logger.info("Something went wrong when performing business logic inside of the " + getClass() + " class.");
			throw new UserNotFoundException("User not found with the provided username and password!"); 
		}
	}

}
