package com.revature.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
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
import com.revature.models.MusicType;
import com.revature.models.User;
import com.revature.template.MessageTemplate;
import com.revature.template.MusicTemplate;
import com.revature.template.RegisterTemplate;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:applicationContext.xml"),
	@ContextConfiguration("classpath:dispatcherContext.xml")
})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class MusicTest {

	@Autowired
	private static SessionFactory sessionFactory;
	
	@Autowired
	WebApplicationContext webApplicationContext;
		
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
//	@BeforeAll
//	@Transactional
//	static void allSetup() {
//		Session sessionF = sessionFactory.getCurrentSession();
//		
//		Transaction txMusicType = sessionF.beginTransaction();
//		
//		MusicType mtTrack = new MusicType(1, "track");
//		MusicType mtArtist = new MusicType(2, "artist");
//		sessionF.persist(mtTrack);
//		sessionF.persist(mtArtist);
//		
//		txMusicType.commit();
//	}
//	
//	@BeforeEach
//	void setup() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//		this.objectMapper = new ObjectMapper();
//	}
//	
//	@Test
//	@Order(1)
//	@Transactional
//	@Commit
//	void testAddTrack() throws Exception {
//		MockHttpSession session = new MockHttpSession();
//		
//		MusicList musicList = new MusicList();
//		musicList.setMusic_list_id(1);
//		User user = new User(1, "John", "Doe", "jdoe1", "password", "jdoe1@company.net", musicList);
//		session.setAttribute("loggedInUser", user);
//		
//		RegisterTemplate registerTemplate = new RegisterTemplate("John", "Doe", "jdoe1", "password", "jdoe1@company.net");
//		String registerTemplateJson = objectMapper.writeValueAsString(registerTemplate);
//		
//		// what we actually want here
//		MusicTemplate musicTemplate = new MusicTemplate("TestSong", "YoungSheldon", "123456", "picHere", "track");
//		String musicTemplateJson = objectMapper.writeValueAsString(musicTemplate);
//		MockHttpServletRequestBuilder builderMusic = MockMvcRequestBuilders
//				.post("/addTrack")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(musicTemplateJson)
//				.session(session);
//		
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//					.post("/addUser")
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(registerTemplateJson)
//					.session(session);
//		
//		User expected = new User(1, "John", "Doe", "jdoe1", "password", "jdoe1@company.net", musicList);
//		String expectedJsonResponse = objectMapper.writeValueAsString(new MessageTemplate("Now logged in as "
//				+ expected.getUsername()));
//		
//		MvcResult result = this.mockMvc
//				.perform(builderMusic)
//				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//				.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
//		
//		System.out.println("musicTemplateJson is " + musicTemplateJson);
//		System.out.println("Result is: " + result.getResponse().getContentAsString());
//	}

}
