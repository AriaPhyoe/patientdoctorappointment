package com.appointment.config;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/admin/**").permitAll()
				.requestMatchers("/doctor/**").permitAll().requestMatchers("/user/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/css/**", "/ppp/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/css/**", "/ppp/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/css/**", "/images/**", "/js/**", "/assets/**","/components/**","/fonts/**","/ccc/**","/patient/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/css/**", "/images/**", "/js/**", "/assets/**","/components/**","/fonts/**","/ccc/**","/patient/**").permitAll()
				.anyRequest().authenticated())
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
		return http.build();

	}
	
	  
	
}