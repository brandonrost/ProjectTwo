package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.dao.UserDAO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.MusicList;
import com.revature.models.User;
import com.revature.template.RegisterTemplate;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

	MockMvc mockMvc;
	
	@Mock
	UserDAO userDAO;
	
	@InjectMocks
	UserService us;
	
	@BeforeEach
	void setup() {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User user1 = new User(1, "User", "Prime", "user1", "password", "user1@place.com", musicList);
				
		lenient().when(userDAO.getUserByUsernameAndPassword(eq("user1"), eq("password"))).thenReturn(user1);
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "Prime", "user1", "password", "user1@place.com");
		lenient().when(userDAO.registerUser(registerTemplate)).thenReturn(user1);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(us).build();
	}
	
	@Test
	void testLogin_goodUserInfo() throws UserNotFoundException {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User expectedUser = new User(1, "User", "Prime", "user1", "password", "user1@place.com", musicList);
		
		User actualUser = us.login("user1", "password");
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testRegisterUser_goodRegister() throws PersistenceException, BadParameterException, SQLException {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User expectedUser = new User(1, "User", "Prime", "user1", "password", "user1@place.com", musicList);
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "Prime", "user1", "password", "user1@place.com");
		User actualUser = (User) us.registerUser(registerTemplate);
		
		assertEquals(expectedUser, actualUser);
	}

}
