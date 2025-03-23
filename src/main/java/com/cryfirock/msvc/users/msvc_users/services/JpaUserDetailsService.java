package com.cryfirock.msvc.users.msvc_users.services;

import com.cryfirock.msvc.users.msvc_users.entities.User;
import com.cryfirock.msvc.users.msvc_users.models.AccountStatus;
import com.cryfirock.msvc.users.msvc_users.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Throw exception if user not found by username
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(String.format("User with username %s does not exist", username));

        // Create UserDetails object and populate it with user data
        User user = optionalUser.orElseThrow();

        // Populate authorities with user's roles
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // Check if user is enabled
        boolean isEnabled = user.getAccountStatus() == AccountStatus.ACTIVE;

        // Return UserDetails object
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                isEnabled, true, true, true,
                authorities);
    }

}
