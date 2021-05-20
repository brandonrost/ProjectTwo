package com.revature.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.MusicList;
import com.revature.models.User;
import com.revature.template.RegisterTemplate;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:applicationContext.xml"),
	@ContextConfiguration("classpath:dispatcherContext.xml")
})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOUnitTest {

	@Autowired
	private UserDAO userDAO;
	
	@Test
	@Transactional
	@Order(0)
	@Commit
	void testRegisterUser() {
		RegisterTemplate rt = new RegisterTemplate("John", "Doe", "jdoe1", "password", "jdoe1@company.net");
		
		User user = userDAO.registerUser(rt);
		
		assertTrue(user.getUserID() != 0);
	}

	@Test
	@Transactional
	@Order(1)
	void testGetUserByIdAndPassword_success() {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User expected = new User(1, "John", "Doe", "jdoe1", "password", "jdoe1@company.net", musicList);
		
		
		User actual = userDAO.getUserByUsernameAndPassword("jdoe1", "password");
		
		assertEquals(expected, actual);
	}
}
