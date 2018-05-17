package com.dev.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	protected UserDetailsService userDetailsService; //cette service permet de gerer l'authentification
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; //gere le cryptage de mots de passe 
	@Override//permet de decrire une methode de recherche des users et des roles(gere l'authentification) 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication()
		.withUse("admin").password("1234").roles("ADMIN","USER")
		.and()
		.withUser("user").password("1234").roles("USER");*/
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}
	
    //definir les droits d'acces des users et gere les filtres
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //passer de l'authentification de reference par l'authentification par valeur cad du session au token 
		http.authorizeRequests().antMatchers("/login/**","/register/**").permitAll();    //des actions necessitants pas des authentifications
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**").hasAuthority("ADMIN"); //AUTHORISER TOUS REQUETTE ENVOYER AVEC LA METHODE POST VERS /TASKS/** SI L'UTILISATEUR A LE ROLE ADMIN
	    //http.formLogin() ;//utilise une formulaire d'authentification fournit par Spring security
		http.authorizeRequests().anyRequest().authenticated(); //tous les requettes necessites une authentication
	    http.addFilter(new JwtAuthenticationFilter(authenticationManager()));  //exploitation du filtre
        http.addFilterBefore(new JwtAurizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
