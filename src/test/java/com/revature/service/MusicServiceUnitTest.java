package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.sql.SQLException;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.dao.MusicDAO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DuplicateEntryException;
import com.revature.exceptions.MusicNotAddedException;
import com.revature.models.Music;
import com.revature.models.MusicList;
import com.revature.models.MusicType;
import com.revature.template.MessageTemplate;
import com.revature.template.MusicTemplate;

@ExtendWith(MockitoExtension.class)
class MusicServiceUnitTest {

	MockMvc mockMvc;
	
	@Mock
	MusicDAO musicDAO;
	
	@InjectMocks
	MusicService ms;
	
	@BeforeEach
	void setup() throws SQLException, DuplicateEntryException {
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		MusicType mType = new MusicType(1, "track");
		
		// Can add a new Track
		MusicTemplate mtGood1 = new MusicTemplate("Track", "TheArtist", "id445", "pic.io.place", "track");
		
		Music music1 = new Music(1, "Track", "TheArtist", "id445", "pic.io.place", mType);
		
		lenient().when(musicDAO.addTrack(mtGood1, musicList)).thenReturn(music1);
		
		// Track already exists
		MusicTemplate mtDuplicate = new MusicTemplate("Double", "Ditto", "id7878", "pic.io.pic.io", "track");
		
		MessageTemplate msgDuplicate = new MessageTemplate("Track already exists within the User's Music_List");
		
		lenient().when(musicDAO.addTrack(mtDuplicate, musicList)).thenReturn(msgDuplicate);
		
		// Track does not exist in SpotifyAPI
		MusicTemplate mtZy = new MusicTemplate("zyzyzyzyzyzy", "JDribble", "id0000", "no.pic", "track");
		
		lenient().when(musicDAO.addTrack(mtZy, musicList)).thenThrow(new NoResultException("zyzyzyzyzyzy not found"));
	}
	
	@Test
	void test_addGoodTrack() throws MusicNotAddedException, DuplicateEntryException, BadParameterException {
		MusicType mType = new MusicType(1, "track");
		Music expectedMusic = new Music(1, "Track", "TheArtist", "id445", "pic.io.place", mType);
		
		MusicList musicList = new MusicList();
		musicList.setMusic_list_id(1);
		MusicTemplate mt = new MusicTemplate("Track", "TheArtist", "id445", "pic.io.place", "track");
		
		Music actualMusic = (Music) ms.addTrack(mt, musicList);
		
		assertEquals(expectedMusic, actualMusic);
	}

	@Test
	void test_addDuplicateTrack() throws MusicNotAddedException, DuplicateEntryException, BadParameterException {
		String expectedE = "Could not add Music because track already exists in the database!";
		try {
			MusicList musicList = new MusicList();
			musicList.setMusic_list_id(1);
			MusicTemplate mt = new MusicTemplate("Double", "Ditto", "id7878", "pic.io.pic.io", "track");
			
			ms.addTrack(mt, musicList);
			
			fail("DuplicateEntryException did not occur.");
		} catch (DuplicateEntryException actualE) {
			assertEquals(expectedE, actualE.getMessage());
		}
	}
	
	@Test
	void test_addNonexistentTrack() throws MusicNotAddedException, DuplicateEntryException, BadParameterException {
		String expectedE = "Could not add Music to User's MusicList. Exception: " + "zyzyzyzyzyzy not found";
		try {
			MusicList musicList = new MusicList();
			musicList.setMusic_list_id(1);
			MusicTemplate mt = new MusicTemplate("zyzyzyzyzyzy", "JDribble", "id0000", "no.pic", "track");
			
			ms.addTrack(mt, musicList);
			
			fail("MusicNotAddedException did not occur.");
		} catch (MusicNotAddedException actualE) {
			assertEquals(expectedE, actualE.getMessage());
		}
	}
	
	@Test
	void test_addBadAllParamsTrack() throws MusicNotAddedException, DuplicateEntryException, BadParameterException {
		// Below is an example of the full string that would be shown from this error. All violations are checked
		// in a random order, therefore it is difficult to compare strings
		
		//		String expectedE = "Could not add track because the following validation rules were broken:"
		//				+ "\nmusic_name must not be blank" + "\nmusic_artist must not be blank"
		//				+ "\nspotify_id must not be blank" + "\nmusic_pic must not be blank"
		//				+ "\nmusic_type must not be blank";
		
		String expectedHeaderMessage = "Could not add track because the following validation rules were broken:";
		String expectedMusicName = "music_name must not be blank";
		String expectedMusicArtist = "music_artist must not be blank";
		String expectedSpotifyId = "spotify_id must not be blank";
		String expectedMusicPic = "music_pic must not be blank";
		String expectedMusicType = "music_type must not be blank";
		
		try {
			MusicList musicList = new MusicList();
			musicList.setMusic_list_id(1);
			MusicTemplate mt = new MusicTemplate("","","","","");
			
			ms.addTrack(mt, musicList);
			
			fail("BadParameterException did not occur. All fields are empty");
		} catch (BadParameterException actualE) {
			boolean allTrue = true;
			
			if (!actualE.getMessage().contains(expectedHeaderMessage)) {
				allTrue = false;
			}
			if (!actualE.getMessage().contains(expectedMusicName)) {
				allTrue = false;
			}
			if (!actualE.getMessage().contains(expectedMusicArtist)) {
				allTrue = false;
			}
			if (!actualE.getMessage().contains(expectedSpotifyId)) {
				allTrue = false;
			}
			if (!actualE.getMessage().contains(expectedMusicPic)) {
				allTrue = false;
			}
			if (!actualE.getMessage().contains(expectedMusicType)) {
				allTrue = false;
			}
			
			assertTrue(allTrue, "BadParameterException, one or more parameters did not properly register as non-blank/blank.");
		}
	}
}
