package com.revature.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.revature.models.Music;
import com.revature.models.MusicType;
import com.revature.service.MusicService;
import com.revature.util.SpotifyBearerToken;

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
				JSONObject jsonPic = (JSONObject) jsonImages.get(0); 
				track.setMusic_pic(jsonPic.get("url").toString()); 
				musicList.add(track); 
			}
			return ResponseEntity.status(200).body(musicList);
			
		} catch (IOException | ParseException e) {
			return ResponseEntity.status(400).body("An error occured while searching SpotifyAPI for track. Exception: " + e.getMessage());
		}
	}
}
