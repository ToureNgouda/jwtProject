package com.dev.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long>{
	//permet de chercher l'utilisateur sachant son username 
	public AppUser findByUsername(String username);

}
