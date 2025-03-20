package com.cryfirock.msvc.users.msvc_users.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    /**
     * To make it a reusable component
     * 
     * Allow password encryption
     * 
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * To make it a reusable component
     * 
     * Authorizing access to API routes
     * 
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/users")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(csrf -> csrf
                        .disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
