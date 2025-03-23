package com.cryfirock.msvc.users.msvc_users.repositories;

import com.cryfirock.msvc.users.msvc_users.entities.Role;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
