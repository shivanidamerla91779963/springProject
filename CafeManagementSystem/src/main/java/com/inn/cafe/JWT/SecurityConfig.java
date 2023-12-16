package com.inn.cafe.JWT;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.BeanIds;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
/*
 import com.springjwt.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 */


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig  {

	@Autowired
	CustomerUserDetailsService customerUsersDetailsService;
	
	@Autowired
	JwtFilter jwtFilter;
	
	
	
	@Bean
	public PasswordEncoder passEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
	
	
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOrigin("http://localhost:4200");  // Replace with your actual front-end origin
	    configuration.addAllowedHeader("*");
	    configuration.addAllowedMethod("*");
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();///user/**","/category/**","/product/**","/bill/**","/dashboard/**
	    source.registerCorsConfiguration("/**", configuration);
		
		
		return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/login","/user/signup","/user/forgotPassword").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/user/**","/category/**","/product/**","/bill/**","/dashboard/**")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().
                cors().configurationSource(source).and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
	
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Update with your frontend origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // Allow credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	
	
	
	
	/*
	 http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/user/login", "/user/signup", "/user/forgotPassword").permitAll()
                .antMatchers("/user/**", "/category/**", "/product/**", "/bill/**", "/dashboard/**")
                .authenticated()
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .cors() // Enable CORS
                .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	 */
	
	
/*	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.cors().configurationSource(request->new CorsConfiguration().applyPermitDefaultValues())
		.and()
		.csrf()
		.disable()
		.authorizeRequests()
		.requestMatchers("/user/login","/user/signup","/user/forgotPassword")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore("", UsernamePasswordAuthenticationFilter.class);
	}   
*/
		
	
}
