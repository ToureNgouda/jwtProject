package com.dev.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AppRoles {
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public AppRoles(String roleName) {
		super();
		this.roleName = roleName;
	}
	public AppRoles() {
		super();
	}
	public AppRoles(Long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	@Id
	@GeneratedValue
	private Long id;
	private String roleName;

}
