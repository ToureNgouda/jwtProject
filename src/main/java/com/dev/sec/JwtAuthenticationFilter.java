package com.dev.sec;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.annotation.SystemProfileValueSource;

import com.dev.entities.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
   
	private AuthenticationManager authenticationManager;
	

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AppUser appUser=null;
		try {
			appUser= new ObjectMapper().readValue(request.getInputStream(),AppUser.class); //permet de deserealizer les donnees recupere dans le corps de la requette
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("***********");
		System.out.println("username:"+appUser.getUsername());
		System.out.println("password:"+appUser.getPassword());

		return authenticationManager.authenticate(
				new  UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User springUser= (User) authResult.getPrincipal();                  //recupere tous les info sur l'utilisateur(username et password)
       String jwt=Jwts.builder()
    		   .setSubject(springUser.getUsername())                      //definir le sujet du token qui est le username 
    		   .setExpiration(new Date(System.currentTimeMillis()+SecureConstant.EXPIRATION_TIME)) //ajout de la date expiration du token 
    		   .signWith(SignatureAlgorithm.HS256, SecureConstant.SECRET) //ajouter un algorithme de hachage et ajout d'un signature
    		   .claim("roles", springUser.getAuthorities())               //definir les roles 
    		   .compact();   //compacter en base 64 url
           response.addHeader(SecureConstant.HEADEAR_STRING, SecureConstant.TOKEN_PREFIX+jwt);  //ajout du token dans le header 
		
	}
}
