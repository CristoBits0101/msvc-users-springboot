package com.cryfirock.msvc.users.msvc_users.security;

/**
 * Dependencies
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

// Configuration class for application security
@Configuration
public class SpringSecurityConfig {

        /**
         * Beans
         * 
         * @return BCryptPasswordEncoder
         */
        @Bean
        PasswordEncoder passwordEncoder() {
                // Use BCrypt to encrypt and compare passwords
                return new BCryptPasswordEncoder();
        }

        /**
         * Configure route access and set rules
         * 
         * @param http
         * @return http rules
         * @throws Exception
         */
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                // Sets the type of access to the routes
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(HttpMethod.GET, "/api/users")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/users")
                                                .permitAll()
                                                .anyRequest()
                                                .authenticated())
                                // Disable CSRF protection
                                .csrf(csrf -> csrf
                                                .disable())
                                // Disable session cookies
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                // Build the configuration
                                .build();
        }

}
