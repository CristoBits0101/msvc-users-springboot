package com.cryfirock.msvc.users.msvc_users.repositories;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

// The interface contains methods with pre-built queries
public interface UserRepository extends CrudRepository<User, Long> {

    // Custom methods for validating
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    // Search for the user by their account name
    Optional<User> findByUsername(String username);

}
