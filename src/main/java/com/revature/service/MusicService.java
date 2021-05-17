package com.revature.service;

import java.util.List;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.MusicDAO;
import com.revature.models.Music;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Service
public class MusicService {

	Logger logger = LoggerFactory.getLogger(MusicService.class);

	private static final Validator VALIDATOR = Validation.byDefaultProvider().configure()
			.messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory().getValidator();

	
}
