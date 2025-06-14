package com.crafter.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crafter.security.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	final JWTService jwtService;
	
	@Autowired
	private final UserDetailsService userDetailService;
	
	public JwtAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailService) {
		this.jwtService = jwtService;
		this.userDetailService = userDetailService;
	}
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String authHeader = request.getHeader("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {  // Add a space after Bearer
	        final String jwtToken = authHeader.substring(7);
	        String userName = jwtService.extractUserName(jwtToken);
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	        if (userName != null && auth == null) {
	            UserDetails userdetail = userDetailService.loadUserByUsername(userName);

	            if (jwtService.isTokenValid(jwtToken, userdetail)) {
	                UsernamePasswordAuthenticationToken userNamePwdAuthToken =
	                        new UsernamePasswordAuthenticationToken(userdetail, null, userdetail.getAuthorities());
	                userNamePwdAuthToken.setDetails(
	                        new WebAuthenticationDetailsSource().buildDetails(request)
	                );
	                SecurityContextHolder.getContext().setAuthentication(userNamePwdAuthToken);
	            }
	        }
	    }

	    filterChain.doFilter(request, response);  // Always call at the end
	}


}
