package com.revature.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.revature.exceptions.DuplicateEntryException;
import com.revature.models.Music;
import com.revature.models.MusicList;
import com.revature.models.MusicType;
import com.revature.template.MusicTemplate;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:applicationContext.xml"),
	@ContextConfiguration("classpath:dispatcherContext.xml")
})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MusicDAOUnitTest {

	@Autowired
	MusicDAO musicDAO;
	
//	@Test
//	void testAddTrackHappyPath() throws SQLException, DuplicateEntryException {
//		MusicType mType = new MusicType(1, "music_type");
//		MusicTemplate mt = new MusicTemplate("name", "spotify_id", "music_pic", "music_type");
//		MusicList ml = new MusicList();
//		ml.setMusic_list_id(1);
//		Music expected = new Music(1, "name", "spotify_id", "music_pic", mType);
//		
//		System.out.println(mt);
//		System.out.println(ml);
//		
//		Music actual = (Music) musicDAO.addTrack(mt, ml);
//		
//		assertEquals(expected, actual);
//	}

}
