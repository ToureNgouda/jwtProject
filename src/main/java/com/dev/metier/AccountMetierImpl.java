package com.dev.metier;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.dao.RolesRepository;
import com.dev.dao.UserRepository;
import com.dev.entities.AppRoles;
import com.dev.entities.AppUser;

@Service
@Transactional
public class AccountMetierImpl implements AccountMetier {
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
 
	
	
	public AppUser saveUser(AppUser user) {
		String hashp=bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashp);
		return userRepository.save(user);
	}

	@Override
	public AppRoles saveRole(AppRoles role) {
		
		return rolesRepository.save(role);
	}


	public void AddRoleToUse(String username, String roleName) {
		AppUser user = userRepository.findByUsername(username);
		AppRoles roles = rolesRepository.findByRoleName(roleName);
		user.getRoles().add(roles);
		
	}


	public AppUser getuser(String username) {
		
		return userRepository.findByUsername(username);
	}

}
