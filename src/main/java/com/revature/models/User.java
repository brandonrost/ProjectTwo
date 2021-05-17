package com.revature.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "User",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})}
		)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID; 
	
	@Column(name = "first_name",
			nullable = false)
	private String firstName;
	
	@Column(name = "last_name",
			nullable = false)
	private String lastName;
	
	@Column(name = "username",
			nullable = false)
	private String username; 
	
	@Column(name = "password",
			nullable = false)
	private String password; 
	
	@Column(name = "email",
			nullable = false,
			unique = true)
	private String email; 
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
	@JoinColumn(name = "music_list_id")
	private MusicList music_list; 

}
