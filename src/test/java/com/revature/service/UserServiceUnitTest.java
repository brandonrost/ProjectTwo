package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;

import java.sql.SQLException;

import javax.persistence.NoResultException;
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
	
	final String password = "wVz2T+3RlmM1F78VcdpSQdf1kT51ZQjjf9ywaKGRxGU=";
	
	@BeforeEach
	void setup() {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User user1 = new User(1, "User", "Prime", "user1", password, "user1@place.com", musicList);
				
		lenient().when(userDAO.getUserByUsernameAndPassword(eq("user1"), eq(password))).thenReturn(user1);
		
		lenient().when(userDAO.getUserByUsernameAndPassword(eq("notuser"), eq(password))).thenThrow(new NoResultException());
		
		final String badPassword = "T2JxQyi7d2BSbpuDeLsMJawUwalXyOuCCTjMG/rRr4Y=";
		lenient().when(userDAO.getUserByUsernameAndPassword(eq("user1"), eq(badPassword))).thenThrow(new NoResultException());
		
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "Prime", "user1", password, "user1@place.com");
		lenient().when(userDAO.registerUser(registerTemplate)).thenReturn(user1);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(us).build();
	}
	
	// Login Tests
	@Test
	void testLogin_goodUserInfo() throws UserNotFoundException {		
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User expectedUser = new User(1, "User", "Prime", "user1", password, "user1@place.com", musicList);
		
		User actualUser = us.login("user1", "password");
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testLogin_userDoesNotExistInDb() throws UserNotFoundException {		
		try {
			us.login("notuser", "password");
			fail("UserNotFoundException did not occur. username should be invalid");
		} catch (UserNotFoundException e) {
			assertEquals(e.getMessage(), "User not found with the provided username and password!");
		}
	}
	
	@Test
	void testLogin_userExistUsedIncorrectPassword() throws UserNotFoundException {		
		try {
			us.login("user1", "password123");
			fail("UserNotFoundException did not occur. username should be invalid");
		} catch (UserNotFoundException e) {
			assertEquals(e.getMessage(), "User not found with the provided username and password!");
		}
	}
	
	@Test
	void testLogin_invalidEmptyUsername_invalidEmptyPassword() throws UserNotFoundException {
		User expectedUser = null;
		
		User actualUser = us.login("", "");
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testLogin_invalidEmptyUsername_validPassword() throws UserNotFoundException {
		User expectedUser = null;
		
		User actualUser = us.login("", "password");
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testLogin_validInDbUsername_invalidEmptyPassword() throws UserNotFoundException {
		User expectedUser = null;
		
		User actualUser = us.login("user1", "");
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testLogin_invalidWhiteSpaceUsername_invalidWhiteSpacePassword() throws UserNotFoundException {
		User expectedUser = null;
		
		User actualUser = us.login("  ", "    ");
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testLogin_invalidWhiteSpaceUsername_validPassword() throws UserNotFoundException {
		User expectedUser = null;
		
		User actualUser = us.login("    ", "password");
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testLogin_validInDbUsername_invalidWhiteSpacePassword() throws UserNotFoundException {
		User expectedUser = null;
		
		User actualUser = us.login("user1", "    ");
		
		assertEquals(expectedUser, actualUser);
	}
	
	// RegisterUser Tests
	@Test
	void testRegisterUser_goodRegister() throws PersistenceException, BadParameterException, SQLException {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User expectedUser = new User(1, "User", "Prime", "user1", password, "user1@place.com", musicList);
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "Prime", "user1", "password", "user1@place.com");
		User actualUser = (User) us.registerUser(registerTemplate);
				
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testRegisterUser_emptyFirstName_otherFieldsGood() throws PersistenceException, BadParameterException, SQLException {
		User expectedUser = null;
		
		RegisterTemplate registerTemplate = new RegisterTemplate("", "Prime", "user1", "password", "user1@place.com");
		User actualUser = (User) us.registerUser(registerTemplate);
		
		assertEquals(expectedUser, actualUser);
	}

	@Test
	void testRegisterUser_emptyLastName_otherFieldsGood() throws PersistenceException, BadParameterException, SQLException {
		User expectedUser = null;
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "", "user1", "password", "user1@place.com");
		User actualUser = (User) us.registerUser(registerTemplate);
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testRegisterUser_emptyUsername_otherFieldsGood() throws PersistenceException, BadParameterException, SQLException {
		User expectedUser = null;
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "Prime", "", "password", "user1@place.com");
		User actualUser = (User) us.registerUser(registerTemplate);
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testRegisterUser_emptyPassword_otherFieldsGood() throws PersistenceException, BadParameterException, SQLException {
		User expectedUser = null;
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "Prime", "user1", "", "user1@place.com");
		User actualUser = (User) us.registerUser(registerTemplate);
		
		assertEquals(expectedUser, actualUser);
	}
	
	@Test
	void testRegisterUser_emptyEmail_otherFieldsGood() throws PersistenceException, BadParameterException, SQLException {
		User expectedUser = null;
		
		RegisterTemplate registerTemplate = new RegisterTemplate("User", "Prime", "user1", "password", "");
		User actualUser = (User) us.registerUser(registerTemplate);
		
		assertEquals(expectedUser, actualUser);
	}
}