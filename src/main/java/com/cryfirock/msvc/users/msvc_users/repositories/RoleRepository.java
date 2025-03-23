package com.cryfirock.msvc.users.msvc_users.repositories;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.entities.Role;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
