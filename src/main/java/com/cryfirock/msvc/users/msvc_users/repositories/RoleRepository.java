package com.cryfirock.msvc.users.msvc_users.repositories;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.Role;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

// The interface contains methods with pre-built queriesF
public interface RoleRepository extends CrudRepository<Role, Long> {

    // Custom method to find role by name
    Optional<Role> findByName(String name);

}
