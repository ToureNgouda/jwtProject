package com.dev.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAurizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Headers", "Origin, Accept,"
				+ "X-Requested-With, Content-Type, Access-Control-Request-Method,"
				+ "Access-Control-Request-Headers, Authorization");
	  res.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
		String jwt = req.getHeader(SecureConstant.HEADEAR_STRING);       //recuper le token dans la requette 
	  System.out.println(jwt);
	  if (jwt==null || !jwt.startsWith(SecureConstant.TOKEN_PREFIX))  //test si le token est  null ou bien si le token ne commence pas par le prefix
		  {
		filterChain.doFilter(req, res); //passer au filtre suivant
	}
	
	  Claims claims =Jwts.parser()
			  .setSigningKey(SecureConstant.SECRET) //signer le token
			  .parseClaimsJws(jwt.replace(SecureConstant.TOKEN_PREFIX, " ")) //remplace le prefix par une chaine vide
			  .getBody();  //recupere le token signe
	  String username = claims.getSubject(); //recupere le username
	  ArrayList<Map<String, String>> roles=  (ArrayList<Map<String, String>>)
			  claims.get("roles");
	  Collection<GrantedAuthority> authorities = new ArrayList<>();
	  roles.forEach(r ->{
		  authorities.add(new SimpleGrantedAuthority(r.get("authority")));
	  });
	  UsernamePasswordAuthenticationToken authenticationUser =new UsernamePasswordAuthenticationToken(username,null, authorities);
	  SecurityContextHolder.getContext().setAuthentication(authenticationUser); //chargement de l'utilisateur authentifie dans le context de security de spring
	  filterChain.doFilter(req, res);
	  
	  
			  
	}
	

}
