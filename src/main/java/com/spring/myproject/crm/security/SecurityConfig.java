package com.spring.myproject.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable()
			.authorizeHttpRequests()
				.requestMatchers("/index", "/user", "/task", "/profile/**").hasAnyRole("ADMIN", "MANAGER", "MEMBER")
				.requestMatchers("/task/**", "/project/**").hasAnyRole("ADMIN", "MANAGER")
				.requestMatchers("/dashboard").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and().formLogin()
				.defaultSuccessUrl("/index", true)
				.permitAll()
			.and().exceptionHandling().accessDeniedPage("/403");
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
