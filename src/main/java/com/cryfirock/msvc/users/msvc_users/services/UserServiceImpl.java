package com.cryfirock.msvc.users.msvc_users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cryfirock.msvc.users.msvc_users.entities.Role;
import com.cryfirock.msvc.users.msvc_users.entities.User;
import com.cryfirock.msvc.users.msvc_users.repositories.RoleRepository;
import com.cryfirock.msvc.users.msvc_users.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Writing methods
     */
    @Override
    @Transactional
    public User save(User user) {
        // Assign user role if present
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        optionalRoleUser.ifPresent(roles::add);

        // Assign admin role if present
        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        // Assign roles to user
        user.setRoles(roles);

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user and return el user
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        // Find user by id
        Optional<User> optionalUser = userRepository.findById(id);

        // Check if user already exists
        if (optionalUser.isPresent()) {

            // Get user to update
            User userToUpdate = optionalUser.get();

            // Update user fields
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userToUpdate.setDob(user.getDob());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setAccountStatus(user.getAccountStatus());

            // Update roles if user is not admin
            if (!user.isAdmin()) {
                // We added the list that modifies the roles
                List<Role> roles = new ArrayList<>();

                // Always assign the user role if it exists
                Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");

                // Add user role to roles list
                optionalRoleUser.ifPresent(roles::add);

                // Set user roles
                userToUpdate.setRoles(roles);
            }

            // Update user in database
            return Optional.of(userRepository.save(userToUpdate));
        }

        // Return empty optional if user does not exist
        return optionalUser;
    }

    /**
     * Reading methods
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Removal methods
    @Override
    @Transactional
    public Optional<User> deleteUser(User user) {
        Optional<User> userToDelete = userRepository.findById(user.getId());
        userToDelete.ifPresent(userRepository::delete);
        return userToDelete;
    }

}
