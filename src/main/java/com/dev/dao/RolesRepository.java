package com.dev.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.entities.AppRoles;

public interface RolesRepository extends JpaRepository<AppRoles, Long>{
	public AppRoles findByRoleName(String roleName);

}
