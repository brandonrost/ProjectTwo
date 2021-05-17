package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class MusicType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int music_type_id; 
	
	@Column(name = "music_type",
			nullable = false)
	private String music_type; 

}
