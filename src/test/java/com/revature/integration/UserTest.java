package com.revature.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.MusicList;
import com.revature.models.User;
import com.revature.template.LoginTemplate;
import com.revature.template.MessageTemplate;
import com.revature.template.RegisterTemplate;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:applicationContext.xml"),
	@ContextConfiguration("classpath:dispatcherContext.xml")
})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class UserTest {

	@Autowired
	WebApplicationContext webApplicationContext;
		
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.objectMapper = new ObjectMapper();
	}
	
	@Test
	@Order(1)
	@Transactional
	@Commit
	void testAddUser_andReceiveGoodJsonResponse() throws Exception {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		
		RegisterTemplate registerTemplate = new RegisterTemplate("John", "Doe", "jdoe1", "password", "jdoe1@company.net");
		String registerTemplateJson = objectMapper.writeValueAsString(registerTemplate);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/addUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(registerTemplateJson);
		
		User expected = new User(1, "John", "Doe", "jdoe1", "password", "jdoe1@company.net", musicList);
		String expectedJsonResponse = objectMapper.writeValueAsString(new MessageTemplate("Now logged in as "
				+ expected.getUsername()));
		
		MvcResult result = this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	@Order(2)
	void testLogout_withSignedInUser() throws Exception {
		MockHttpSession session = new MockHttpSession();
		
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		User user = new User(1, "John", "Doe", "jdoe1", "password", "jdoe1@company.net", musicList);
		session.setAttribute("loggedInUser", user);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/logout")
				.contentType(MediaType.APPLICATION_JSON)
				.session(session);
		
		MessageTemplate expected = new MessageTemplate("Successfully logged out user: " + user);
		String expectedJsonResponse = objectMapper.writeValueAsString(expected);
		
		MvcResult result = this.mockMvc
				.perform(builder)
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	@Order(2)
	void testLogout_withNoSignedInUser() throws Exception {
		MockHttpSession session = new MockHttpSession();
				
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/logout")
				.contentType(MediaType.APPLICATION_JSON)
				.session(session);
		
		MessageTemplate expected = new MessageTemplate("Could not log user out. No user was logged in.");
		String expectedJsonResponse = objectMapper.writeValueAsString(expected);
		
		MvcResult result = this.mockMvc
				.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	@Order(3)
	void testLogin_withGoodUser() throws Exception {
		MessageTemplate expected = new MessageTemplate("Now logged in as jdoe1");
		String expectedJsonResponse = objectMapper.writeValueAsString(expected);
		
		LoginTemplate lt = new LoginTemplate("jdoe1", "password");
		String loginTemplateJson = objectMapper.writeValueAsString(lt);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginTemplateJson);
		
		MvcResult result = this.mockMvc
				.perform(builder)
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
}
