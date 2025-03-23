package com.cryfirock.msvc.users.msvc_users.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cryfirock.msvc.users.msvc_users.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    // Check if data is already registered
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    // Find user by username for authentication
    Optional<User> findByUsername(String username);

}
