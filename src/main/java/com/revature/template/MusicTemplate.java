package com.revature.template;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class MusicTemplate {
	@NotBlank
	private String name;
	@NotBlank
	private String spotify_id;
	@NotBlank
	private String music_pic; 
	@NotBlank
	private String music_type; 
	

}
