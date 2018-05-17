package com.dev.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class AppUser {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String username;
	private String password;
	@ManyToMany(fetch=FetchType.EAGER) //on charge les roles a chaque fois qu'on charger un user
	private Collection<AppRoles> roles = new ArrayList<>();
	
   
	public AppUser() {
		super();
	}
	public AppUser(Long id, String username, String password, Collection<AppRoles> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	public AppUser(String username, String password, Collection<AppRoles> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<AppRoles> getRoles() {
		return roles;
	}
	public void setRoles(Collection<AppRoles> roles) {
		this.roles = roles;
	}
	
}
