package com.cryfirock.msvc.users.msvc_users.services;

import com.cryfirock.msvc.users.msvc_users.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Writing methods
    User save(User user);

    Optional<User> update(Long id, User user);

    // Reading methods
    List<User> findAll();

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    // Removal methods
    Optional<User> deleteUser(User user);

}
