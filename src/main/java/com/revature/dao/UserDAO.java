package com.revature.dao;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.MusicList;
import com.revature.models.User;
import com.revature.template.RegisterTemplate;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public User getUserByUsernameAndPassword(String username, String password) {
		Session session = sessionFactory.getCurrentSession();

		User user = (User) session.createQuery("FROM User u WHERE u.username = :user AND u.password = :pass")
				.setParameter("user", username).setParameter("pass", password).getSingleResult();

		return user;
	}

	@Transactional(rollbackFor = {PersistenceException.class, SQLException.class})
	public User registerUser(RegisterTemplate registerTemplate) {
		Session session = sessionFactory.getCurrentSession();

		User user = new User(
				0, registerTemplate.getFirstName(),
				registerTemplate.getLastName(),
				registerTemplate.getUsername(),
				registerTemplate.getPassword(),
				registerTemplate.getEmail(), new MusicList());
		session.persist(user);		

		return user;
	}

}
