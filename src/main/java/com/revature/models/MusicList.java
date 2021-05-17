package com.revature.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class MusicList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int music_list_id; 
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},
	fetch = FetchType.EAGER)
	@JoinTable(
			name = "Music_List_Music",
			joinColumns = {@JoinColumn(name = "music_list_id")},
			inverseJoinColumns = {@JoinColumn(name = "music_id")}
			)
	private List<Music> music_list = new ArrayList<Music>(); 
	
	
}
