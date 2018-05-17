package com.dev;



import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dev.dao.TaskRepository;
import com.dev.entities.AppRoles;
import com.dev.entities.AppUser;
import com.dev.entities.Task;
import com.dev.metier.AccountMetier;

@SpringBootApplication
public class JwtSpringApplication implements CommandLineRunner {
   @Autowired
   private TaskRepository taskRepository;
   @Autowired
   private AccountMetier accountMetier;
	public static void main(String[] args) {
		SpringApplication.run(JwtSpringApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder getBcpe() {
		return new BCryptPasswordEncoder(); //instanciation de la classe Bcrip.. pour pouvoir l'injecter partout dans l'application
	}

	@Override
	public void run(String... args) throws Exception {
	
		accountMetier.saveUser(new AppUser(null,"admin", "1234", null));
		accountMetier.saveUser(new AppUser(null,"user", "1234", null));
		accountMetier.saveRole(new AppRoles(null,"ADMIN"));
		accountMetier.saveRole(new AppRoles(null,"USER"));
        accountMetier.AddRoleToUse("admin", "ADMIN");
        accountMetier.AddRoleToUse("admin", "USER");
        accountMetier.AddRoleToUse("user", "USER");


		

		
	Stream.of("T1","T2","T3").forEach(t ->{
		taskRepository.save(new Task(null,t));
	});
	 taskRepository.findAll().forEach(t ->{
		System.out.println(t.getTaskName()); 
	 });
	 
	
	}
}
