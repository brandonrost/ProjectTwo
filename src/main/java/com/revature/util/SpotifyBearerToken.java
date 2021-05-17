package com.revature.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SpotifyBearerToken {

	public String getSpotifyBearerToken() {
		String urlParameters = "grant_type=client_credentials";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		try {
			URL url = new URL("https://accounts.spotify.com/api/token");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.addRequestProperty("Authorization",
					"Basic YzZjMzljYWNlY2ZlNDA5Yzk1OGJhMTdkZDI2NDA4NGY6M2QwMTJhZjE2MWUwNDFhNjgyYmIxZTU1NDZhNGY2ZjQ=");
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.write(postData);		
			}
			InputStream responseStream = connection.getInputStream();
			JSONParser jsonParser = new JSONParser();

			JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(responseStream, "UTF-8"));
			
			return jsonObject.get("access_token").toString(); 

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null; 
		}
	}

}
