package com.cryfirock.msvc.users.msvc_users.repositories;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.User;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
