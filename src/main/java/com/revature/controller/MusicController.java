package com.revature.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DuplicateEntryException;
import com.revature.exceptions.MusicNotAddedException;
import com.revature.exceptions.UserNotLoggedInException;
import com.revature.models.Music;
import com.revature.models.MusicList;
import com.revature.models.MusicType;
import com.revature.models.User;
import com.revature.service.MusicService;
import com.revature.template.MessageTemplate;
import com.revature.template.MusicTemplate;
import com.revature.util.SpotifyBearerToken;

import jakarta.validation.Valid;

@Controller
public class MusicController {
	Logger logger = LoggerFactory.getLogger(MusicController.class);

	@Autowired
	private MusicService musicService;

	@Autowired
	private HttpServletRequest request;
	@SuppressWarnings("unused")
	@Autowired
	private HttpServletResponse response;

	@GetMapping(path = "searchTrack/{queryType}/{trackName}")
	public ResponseEntity<Object> searchForTrack(@PathVariable("queryType") String queryType,
			@PathVariable("trackName") String trackName) throws IOException {

		// Create a neat value object to hold the URL
		URL url = new URL("https://api.spotify.com/v1/search?q=" + trackName + "&type=" + queryType);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		connection.addRequestProperty("Content-Type", "application/json");
		SpotifyBearerToken sbt = new SpotifyBearerToken(); 
		connection.addRequestProperty("Authorization",
				"Bearer " + sbt.getSpotifyBearerToken());
		
		InputStream responseStream = connection.getInputStream(); 
		List<Music> musicList = new ArrayList<Music>(); 
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(
			      new InputStreamReader(responseStream, "UTF-8"));
			JSONObject items = (JSONObject) jsonObject.get("tracks");
			JSONArray tracks = (JSONArray) items.get("items");
			for(int i=0; i<tracks.size(); i++) {
				JSONObject jsonTrack = (JSONObject) tracks.get(i); 
				Music track = new Music(); 
				track.setMusic_name(jsonTrack.get("name").toString());
				track.setSpotify_id(jsonTrack.get("id").toString()); 
				track.setMusic_type(new MusicType(1, "track"));
				JSONObject jsonAlbum = (JSONObject) jsonTrack.get("album");
				JSONArray jsonImages = (JSONArray) jsonAlbum.get("images"); 
				JSONArray jsonArtists = (JSONArray) jsonAlbum.get("artists");
				JSONObject jsonArtist = (JSONObject) jsonArtists.get(0); 
				JSONObject jsonPic = (JSONObject) jsonImages.get(0);
				track.setMusic_artist(jsonArtist.get("name").toString()); 
				track.setMusic_pic(jsonPic.get("url").toString()); 
				musicList.add(track); 
			}
			return ResponseEntity.status(200).body(musicList);
			
		} catch (IOException | ParseException e) {
			return ResponseEntity.status(400).body("An error occured while searching SpotifyAPI for track. Exception: " + e.getMessage());
		}
	}
	
	@GetMapping(path = "/track/getRecommended")
	public ResponseEntity<Object> getRecommended() throws IOException {
		User user;		
		try {
			HttpSession session = request.getSession(true);
			user = (User) session.getAttribute("loggedInUser");
			if(user.getMusic_list().getMusic_list() != null) {
				StringBuilder sb = new StringBuilder();
				sb.append("?market=US"); 
				
				List<Music> usersMusicList = new ArrayList<Music>(user.getMusic_list().getMusic_list());  
				List<Music> randomTracks = new ArrayList<Music>(); 
				
				Random rand = new Random();
				
			    int numberOfElements = 5;
			    
			    
			    if (user.getMusic_list().getMusic_list().size() <= numberOfElements) {
			    	for(int i = 0; i < user.getMusic_list().getMusic_list().size(); i++) {
			    		randomTracks.add(user.getMusic_list().getMusic_list().get(i)); 			    		
			    	}
			    }else {
			    	for (int i = 0; i < numberOfElements; i++) {
				        int randomIndex = rand.nextInt(usersMusicList.size());
				        Music randomElement = usersMusicList.get(randomIndex);
				        usersMusicList.remove(randomIndex);
				        randomTracks.add(randomElement);
				    }			    	
			    }
			    
				for(Music m:randomTracks) {
					sb.append("&seed_tracks=" + m.getSpotify_id());					
				}
				sb.append("&min_energy=0.4&min_popularity=50"); 
				
				URL url = new URL("https://api.spotify.com/v1/recommendations" + sb);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json");
				connection.addRequestProperty("Content-Type", "application/json");
				SpotifyBearerToken sbt = new SpotifyBearerToken(); 
				connection.addRequestProperty("Authorization",
						"Bearer " + sbt.getSpotifyBearerToken());
				
				InputStream responseStream = connection.getInputStream();
				
				List<Music> musicList = new ArrayList<Music>(); 
				JSONParser jsonParser = new JSONParser();
				try {
					JSONObject jsonObject = (JSONObject)jsonParser.parse(
					      new InputStreamReader(responseStream, "UTF-8"));
					JSONArray tracks = (JSONArray) jsonObject.get("tracks");
					for(int i=0; i<tracks.size(); i++) {
						JSONObject jsonTrack = (JSONObject) tracks.get(i); 
						Music track = new Music(); 
						track.setMusic_name(jsonTrack.get("name").toString());
						track.setSpotify_id(jsonTrack.get("id").toString()); 
						track.setMusic_type(new MusicType(1, "track"));
						JSONObject jsonAlbum = (JSONObject) jsonTrack.get("album");
						JSONArray jsonImages = (JSONArray) jsonAlbum.get("images");
						JSONArray jsonArtists = (JSONArray) jsonAlbum.get("artists");
						JSONObject jsonArtist = (JSONObject) jsonArtists.get(0); 
						JSONObject jsonPic = (JSONObject) jsonImages.get(0); 
						track.setMusic_pic(jsonPic.get("url").toString()); 
						track.setMusic_artist(jsonArtist.get("name").toString());
						musicList.add(track); 
					}
					return ResponseEntity.status(200).body(musicList);
					
				} catch (IOException | ParseException e) {
					return ResponseEntity.status(400).body("An error occured while searching SpotifyAPI for track recommendations. Exception: " + e.getMessage());
				}
				
			}else {
				throw new UserNotLoggedInException("Must be registered and logged in to an Account to access this functionality!"); 
			}			
		} catch (UserNotLoggedInException e) {
			ResponseEntity.status(401).body(new MessageTemplate("Must be registered and logged in to an Account to access this functionality!"));			
		}
		return null;
	}
	
	@PostMapping(path = "addTrack")
	public ResponseEntity<Object> addTrack(@RequestBody @Valid MusicTemplate musicTemplate) throws IOException, MusicNotAddedException, DuplicateEntryException, SQLException, UserNotLoggedInException {
		try {
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("loggedInUser");
			MusicList musicList; 
			if(user.getFirstName() == null) {
				musicList = new MusicList(); 
				throw new UserNotLoggedInException(); 
			} else {
				musicList = user.getMusic_list();				
			}
			
			Object serviceObject = musicService.addTrack(musicTemplate, musicList);
			
			if(serviceObject instanceof Music) {
				return ResponseEntity.status(200).body(serviceObject);
			}else {
				return ResponseEntity.status(400).body(serviceObject); 
			}			
		} catch (UserNotLoggedInException e) {
			return ResponseEntity.status(404).body(new MessageTemplate("User must be logged in to utilize this feature of TasteBass!")); 
		} catch (DuplicateEntryException e1) {
			return ResponseEntity.status(404).body(new MessageTemplate("Could not add Music to database because track with this spotify_id already exists.")); 
		} catch (BadParameterException e2) {
			return ResponseEntity.status(404).body(new MessageTemplate("Make sure all required fields are entered correctly before trying to add a track."));			
		}
	}
	
	@GetMapping (path = "getTracks")
	public ResponseEntity<Object> getTracks(){
		try {
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("loggedInUser");
			
			Object serviceObject = musicService.getTracks(user);
			
			if(serviceObject instanceof List<?>) {
				return ResponseEntity.status(200).body(serviceObject); 
			}else {
				throw new BadParameterException(); 
			}
		}catch(BadParameterException e1) {
			return ResponseEntity.status(404).body(new MessageTemplate("Could not obtain User's music_list because they do not currently have any tracks saved to their list."));
		}	
	}
}
