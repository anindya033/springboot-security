package com.crafter.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.crafter.security.service.CustomUserDetailService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	public final JwtAuthenticationFilter jwtAuthenticationFilter ;
	
	private final CustomUserDetailService userDetailsService;
	
	public WebSecurityConfig(CustomUserDetailService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

		try {

			httpSecurity
					.authorizeHttpRequests
					(
							req ->
								req
								.requestMatchers("register").permitAll()
								.requestMatchers("login").permitAll()
								.anyRequest().authenticated()
					)
					//.formLogin(Customizer.withDefaults()) // this is to get default login page
					.httpBasic(Customizer.withDefaults())
					.csrf(csrf -> csrf.disable())
					.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
					;
			// .httpBasic()

			return httpSecurity.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Failed to build SecurityFilterChain", e);
		}

	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	@SuppressWarnings("deprecation")
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
	    provider.setPasswordEncoder(bCryptPasswordEncoder());  // <-- here
		
		return provider;
		
	}
	
	@Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	/*
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User
				.withUsername("user")
				.password("{noop}1234")
				.roles("USER").build();

		UserDetails nikhil = User.withUsername("nikhil")

				// {noop} is a password encoder identifier that tells Spring Security:
				// "Do not encode this password — use it as plain text."
				.password("{noop}nikhil").roles("USER").build();

		return new InMemoryUserDetailsManager(user, nikhil);

	}*/

}
