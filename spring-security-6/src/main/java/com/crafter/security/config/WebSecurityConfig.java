package com.crafter.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

		try {

			httpSecurity.authorizeHttpRequests(req -> req.anyRequest().authenticated())
					.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
					.csrf(csrf -> csrf.disable());
			// .httpBasic()

			return httpSecurity.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Failed to build SecurityFilterChain", e);
		}

	}

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

	}

}
