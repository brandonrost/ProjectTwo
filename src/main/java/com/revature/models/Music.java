package com.revature.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Music {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int music_id; 
	
	@Column(name = "name",
			nullable = false)
	private String music_name; 
	
	@Column(name = "spotify_id",
			nullable = false)
	private String spotify_id; 
	
	@Column(name = "music_pic",
			nullable = true)
	private byte[] music_pic;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name = "music_type",
			nullable = false)
	private MusicType music_type;

}
