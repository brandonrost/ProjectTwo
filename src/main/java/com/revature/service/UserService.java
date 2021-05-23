package com.revature.service;

import java.sql.SQLException;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dao.UserDAO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.template.LoginTemplate;
import com.revature.template.RegisterTemplate;
import com.revature.util.PasswordUtil;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Service
public class UserService {
	
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private static final Validator VALIDATOR =
			  Validation.byDefaultProvider()
			    .configure()
			    .messageInterpolator(new ParameterMessageInterpolator())
			    .buildValidatorFactory()
			    .getValidator();
	
	@Autowired
	private UserDAO userDAO; 
	
	@Transactional(rollbackFor = {UserNotFoundException.class})
	public User login(String username, String password) throws UserNotFoundException {
		try {
			Set<ConstraintViolation<LoginTemplate>> violations = VALIDATOR.validate(new LoginTemplate(username, password));
			if(violations.isEmpty()) {
				String securePW = PasswordUtil.generateSecurePassword(password, "EqdmPh53c9x33EygXpTpcoJvc4VXLK"); 
				password = securePW;  
				User user = userDAO.getUserByUsernameAndPassword(username, password); 
				logger.info("Successfully completed business logic inside of the " + getClass() + " class.");
				return user; 				
			} else {
				for(ConstraintViolation<LoginTemplate> v:violations) {
					logger.warn(v.getMessage() + " : " + v.getMessage());
				}
				return null; 
			}
		} catch (NoResultException e) {
			logger.info("Something went wrong when performing business logic inside of the " + getClass() + " class.");
			throw new UserNotFoundException("User not found with the provided username and password!"); 
		}
	}
	@Transactional(rollbackFor = {BadParameterException.class, SQLException.class, PersistenceException.class})
	public Object registerUser(RegisterTemplate registerTemplate) throws BadParameterException, SQLException, PersistenceException {
		Set<ConstraintViolation<RegisterTemplate>> violations = VALIDATOR.validate(registerTemplate);
		if(violations.isEmpty()) {
			logger.info("Successfully completed business logic inside of the " + getClass() + " class.");			
			String securePW = PasswordUtil.generateSecurePassword(registerTemplate.getPassword(),"EqdmPh53c9x33EygXpTpcoJvc4VXLK"); 
			registerTemplate.setPassword(securePW);
			User user = userDAO.registerUser(registerTemplate);
			return user; 				
		}else {
			for(ConstraintViolation<RegisterTemplate> v:violations) {
				logger.warn(v.getPropertyPath() + ":" + v.getMessage());
			}
			return null; 
		}
	}

}
