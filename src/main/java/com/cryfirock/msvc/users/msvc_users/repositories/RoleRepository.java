package com.cryfirock.msvc.users.msvc_users.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cryfirock.msvc.users.msvc_users.entities.Role;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    // Custom query by attribute name
    Optional<Role> findByName(String name);

}
