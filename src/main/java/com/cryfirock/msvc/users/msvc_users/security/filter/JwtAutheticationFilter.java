package com.cryfirock.msvc.users.msvc_users.security.filter;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.User;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// This filter authenticates users and generates tokens during the login process
public class JwtAutheticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Attributes
     */
    private AuthenticationManager authenticationManager;

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    /**
     * Constructors
     * 
     * @param authenticationManager
     */
    public JwtAutheticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Methods
     * 
     * Attempts to authenticate a user
     * Parse the request body to obtain the username and password credentials
     * Delegate authentication to the provided AuthenticationManager
     * 
     * @param request
     * @param response
     * @return Object with user data and role information
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        // Declaration of a null user that does not point to any object
        User user = null;

        // Create two variables for the user credentials
        String username = null;
        String password = null;

        try {
            // Attempt to parse the incoming request's body into a User object
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            // Extract the username and password from the user object
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e) {
            // Handle exception if the input stream cannot be read
            e.printStackTrace();
        } catch (DatabindException e) {
            // Handle exception if the input cannot be mapped to the User class
            e.printStackTrace();
        } catch (IOException e) {
            // Handle other I/O exceptions
            e.printStackTrace();
        }

        // Create an authentication token with the username and password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password);

        // Authenticate the user using the provided AuthenticationManager
        // Verify that the credentials are valid and match those stored in the database
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * It runs automatically after authentication and doesn't need to be called
     * Generates a JWT token upon successful authentication of the user
     * Adds it to the authorization header of the response
     * It also prepares and sends a response
     * Response containing the token, username and a welcome message
     * 
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // Retrieve the authenticated user
        User user = (User) authResult.getPrincipal();

        // Get the username from the authenticated user
        String username = user.getUsername();

        // Generate a JWT token based on the username
        String token = Jwts
                .builder()
                .subject(username)
                .signWith(SECRET_KEY)
                .compact();

        // Set the token in the Authorization header of the response
        response.addHeader("Authorization", "Bearer " + token);

        // Prepare a response body with the token, username, and a message
        Map<String, String> body = new HashMap<>();

        body.put("token", "token");
        body.put("username", username);
        body.put("message", String.format("Welcome %s! ", username));

        // Write the response body as JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

        // Set the response type and status
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
