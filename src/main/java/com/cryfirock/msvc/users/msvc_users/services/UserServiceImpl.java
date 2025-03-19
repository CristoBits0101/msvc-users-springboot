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

import jakarta.transaction.Transactional;

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

    /**
     * Reading methods
     */
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Removal methods
    @Override
    @Transactional
    public Optional<User> deleteById(User user) {
        Optional<User> userToDelete = userRepository.findById(user.getId());
        userToDelete.ifPresent(userRepository::delete);
        return userToDelete;
    }

}
