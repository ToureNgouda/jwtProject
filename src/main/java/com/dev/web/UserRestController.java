package com.dev.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.AppUser;
import com.dev.metier.AccountMetier;

@RestController
public class UserRestController {

	 @Autowired
	 private AccountMetier accountMetier;
     
	 @PostMapping("/register")
	 public AppUser saveUser(@RequestBody RegisterForm userForm) {
		 if (!userForm.getPassword().equals(userForm.getRepassword())) {
			throw new RuntimeException("you must confirm your password");
		   }
		 AppUser user=accountMetier.getuser(userForm.getUsername());
		 if (user!=null) {
			 throw new RuntimeException("this user already exists");
		}
		 AppUser appUser= new AppUser();
		 appUser.setPassword(userForm.getPassword());
		 appUser.setUsername(userForm.getUsername());
		 accountMetier.saveUser(appUser);
		 accountMetier.AddRoleToUse(userForm.getUsername(),"USER");
		 return appUser;
		 
	}
	 
	 
}
