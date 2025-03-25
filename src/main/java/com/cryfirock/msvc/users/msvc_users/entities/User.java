package com.cryfirock.msvc.users.msvc_users.entities;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.models.AccountStatus;

import com.cryfirock.msvc.users.msvc_users.validations.ExistsByEmail;
import com.cryfirock.msvc.users.msvc_users.validations.ExistsByPhoneNumber;
import com.cryfirock.msvc.users.msvc_users.validations.ExistsByUsername;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

// Entity that maps the users table and autogenerates methods
@AllArgsConstructor
@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    /**
     * Attributes
     * Include annotations that validate database rules
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "{NotEmpty.user.firstName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.firstName}")
    @Size(min = 1, max = 50, message = "{Size.user.firstName}")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "{NotEmpty.user.lastName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.lastName}")
    @Size(min = 1, max = 50, message = "{Size.user.lastName}")
    private String lastName;

    @Column(unique = true)
    @Email(message = "{Email.user.email}")
    @ExistsByEmail
    @NotBlank(message = "{NotBlank.user.email}")
    @Size(min = 1, max = 100, message = "{Size.user.email}")
    private String email;

    @Column(name = "phone_number", unique = true)
    @ExistsByPhoneNumber
    @NotEmpty(message = "{NotEmpty.user.phoneNumber}")
    @Pattern(regexp = "^[0-9]{9,20}$", message = "{Pattern.user.phoneNumber}")
    @Size(min = 9, max = 20, message = "{Size.user.phoneNumber}")
    private String phoneNumber;

    @Column(unique = true)
    @ExistsByUsername
    @NotBlank(message = "{NotBlank.user.username}")
    @Size(min = 1, max = 50, message = "{Size.user.username}")
    private String username;

    @Column(name = "password_hash")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{NotBlank.user.password}")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{NotNull.user.dob}")
    private LocalDate dob;

    @NotBlank(message = "{NotBlank.user.address}")
    private String address;

    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    /**
     * Relationships
     * Include annotations that validate database rules
     */
    @JsonIgnoreProperties({ "users", "handler", "hibernateLazyInitializer" })
    @JoinTable(
            // Table that stores relational foreign keys
            name = "users_roles",

            // Foreign key that belongs to the entity itself
            joinColumns = @JoinColumn(name = "user_id"),

            // Foreign key belonging to the opposing entity
            inverseJoinColumns = @JoinColumn(name = "role_id"),

            // Establishes that relationships are unique
            uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "role_id" }))
    @ManyToMany
    private List<Role> roles;

    /**
     * Transients
     * Includes annotations to exclude
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private boolean admin;

    /**
     * Embedded
     * Class that is embedded in another
     */
    @Embedded
    private Audit audit;

    /**
     * Constructors
     */
    public User() {
        this.audit = new Audit();
    }

    /**
     * Methods
     */
    @PrePersist
    public void prePersistUser() {
        this.accountStatus = AccountStatus.ACTIVE;
    }

}
