package com.cryfirock.msvc.users.msvc_users.services;

import java.util.List;
import java.util.Optional;

import com.cryfirock.msvc.users.msvc_users.entities.User;

public interface UserService {

    // Writing methods
    User save(User user);

    // Reading methods
    List<User> findAll();
    Optional<User> findById(Long id);

    // Removal methods
    Optional<User> deleteById(User user);

}
