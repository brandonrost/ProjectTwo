package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Music;
import com.revature.models.User;

@Repository
public class MusicDAO {

	@Autowired
	private SessionFactory sessionFactory;

	

}
