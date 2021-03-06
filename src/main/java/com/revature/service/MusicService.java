package com.revature.service;

import java.sql.SQLException;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.MusicDAO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DuplicateEntryException;
import com.revature.exceptions.MusicNotAddedException;
import com.revature.exceptions.UserNotLoggedInException;
import com.revature.models.Music;
import com.revature.models.MusicList;
import com.revature.models.User;
import com.revature.template.MessageTemplate;
import com.revature.template.MusicTemplate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Service
public class MusicService {

	Logger logger = LoggerFactory.getLogger(MusicService.class);
	
	@Autowired
	private MusicDAO musicDAO; 

	private static final Validator VALIDATOR = Validation.byDefaultProvider().configure()
			.messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory().getValidator();
	
	@Transactional(rollbackOn = {MusicNotAddedException.class, DuplicateEntryException.class, SQLException.class, ConstraintViolationException.class})
	public Object addTrack(@Valid MusicTemplate musicTemplate, MusicList musicList) throws MusicNotAddedException, DuplicateEntryException, BadParameterException {
		try {
			Set<ConstraintViolation<MusicTemplate>> violations = VALIDATOR.validate(musicTemplate);
			if(violations.isEmpty()) {
				Object music = musicDAO.addTrack(musicTemplate, musicList); 
				if(music instanceof Music) {
					return music; 	
				}else {
					throw new SQLException(); 
				}
			} else {
				for(ConstraintViolation<MusicTemplate> v:violations) {
					logger.warn(v.getPropertyPath().toString() + " : " + v.getMessage());
				}
				MessageTemplate mt = new MessageTemplate("Could not add track because the following validation rules were broken:");
				violations.forEach(violation -> {
					mt.setMessage(mt.getMessage() + "\n" + violation.getPropertyPath() + " " + violation.getMessage());
				});
				throw new BadParameterException(mt.getMessage());  
			}
		} catch (NoResultException e) {
			throw new MusicNotAddedException("Could not add Music to User's MusicList. Exception: " + e.getMessage()); 
		} catch (SQLException e1) {
			throw new DuplicateEntryException("Could not add Music because track already exists in the database!");
		}
	}
	
	@Transactional(rollbackOn = {BadParameterException.class})
	public Object getTracks(User user) throws BadParameterException {
		try {
			Object music = musicDAO.getTracks(user);
			return music; 
		} catch (NoResultException e) {
			throw new BadParameterException("Could not find any tracks in the current User's music_list."); 
		}
	}

}
