package com.revature.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.service.MusicService;

@ExtendWith(MockitoExtension.class)
class MusicControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	MusicService musicService;
	
	@InjectMocks
	MusicController mc;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(mc).build();
	}

	@Test
	void test_searchForTrackReturnsPositivelyFromSpotifyAPI() throws Exception {
		
		this.mockMvc
			.perform(get("/searchTrack/track/options"))
			.andExpect(MockMvcResultMatchers.status().isOk());
			//.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
	}
}
