package com.cryfirock.msvc.users.msvc_users.services;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.User;

import java.util.List;
import java.util.Optional;

// Limits the actions available in the application
public interface UserService {

    // Custom methods for validating
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    // CRUD operations
    List<User> findAll();

    Optional<User> deleteUser(User user);

    Optional<User> findById(Long id);

    Optional<User> update(Long id, User user);

    User save(User user);

}
