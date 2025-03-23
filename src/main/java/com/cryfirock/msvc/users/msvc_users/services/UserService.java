package com.cryfirock.msvc.users.msvc_users.services;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    List<User> findAll();

    Optional<User> deleteUser(User user);

    Optional<User> findById(Long id);

    Optional<User> update(Long id, User user);

    User save(User user);

}
