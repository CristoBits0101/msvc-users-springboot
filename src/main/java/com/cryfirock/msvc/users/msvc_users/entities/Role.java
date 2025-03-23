package com.cryfirock.msvc.users.msvc_users.entities;

/**
 * Dependencies
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Size;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entity that maps the roles table and autogenerates methods
@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    /**
     * Attributes
     * Include annotations that validate database rules
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    @Size(max = 50)
    private String name;

    /**
     * Relationships
     * Include annotations that validate database rules
     */
    @JsonIgnoreProperties({ "roles" })
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
