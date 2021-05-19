package com.revature.dao;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exceptions.DuplicateEntryException;
import com.revature.models.Music;
import com.revature.models.MusicList;
import com.revature.models.MusicType;
import com.revature.template.MessageTemplate;
import com.revature.template.MusicTemplate;

import jakarta.validation.Valid;

@Repository
public class MusicDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public Object findTrack(String spotify_id) {
		Session session = sessionFactory.getCurrentSession(); 
		
		Music music = (Music) session.createQuery("FROM Music m WHERE m.spotify_id = :spotifyid")
				.setParameter("spotifyid", spotify_id).getSingleResult(); 
		
		return music;
	}
	
	@Transactional
	public Object addTrack(@Valid MusicTemplate musicTemplate, MusicList musicList) throws SQLException, DuplicateEntryException {
		Session session = sessionFactory.getCurrentSession();
		
		
		Music music = new Music(); 
		music.setMusic_name(musicTemplate.getName());
		music.setMusic_pic(musicTemplate.getMusic_pic());
		MusicType mt = session.get(MusicType.class, 1);
		music.setMusic_type(mt);
		music.setSpotify_id(musicTemplate.getSpotify_id());
		
		MusicList ml = session.get(MusicList.class, musicList.getMusic_list_id()); 
		try {
			Music musicExists; 
			try {
				musicExists = (Music) findTrack(musicTemplate.getSpotify_id());				
			} catch (Exception e) {
				musicExists = null; 
			}
			
			boolean musicExistsInUser = false; 
			
			for(Music m:ml.getMusic_list()) {
				if(m.getMusic_id() == music.getMusic_id() || m.getMusic_id() == musicExists.getMusic_id()) {
					musicExistsInUser = true; 
				}
			}
			if(musicExists != null) {	
				if(musicExistsInUser) {
					throw new DuplicateEntryException("Track already exists within the User's Music_List"); 				
				}else {
					ml.addToList(musicExists);
					return musicExists; 
				}
			}else {
				session.save(music);
				ml.addToList(music);
				return music; 										
			}			
		}catch(DuplicateEntryException e) {
			return new MessageTemplate(e.getMessage()); 			
		}
	}
}
