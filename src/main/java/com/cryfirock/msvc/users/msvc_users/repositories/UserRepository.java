package com.cryfirock.msvc.users.msvc_users.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cryfirock.msvc.users.msvc_users.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByUsername(String username);

}
