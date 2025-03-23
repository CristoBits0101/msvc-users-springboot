package com.cryfirock.msvc.users.msvc_users.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cryfirock.msvc.users.msvc_users.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    // Custom query by attribute name
    Optional<Role> findByName(String name);

}
