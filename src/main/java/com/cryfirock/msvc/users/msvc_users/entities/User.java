package com.cryfirock.msvc.users.msvc_users.entities;

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

@AllArgsConstructor
@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "{NotEmpty.user.firstName}")
    @Size(min = 1, max = 50, message = "{Size.user.firstName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.firstName}")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "{NotEmpty.user.lastName}")
    @Size(min = 1, max = 50, message = "{Size.user.lastName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.lastName}")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "{NotBlank.user.email}")
    @Size(min = 1, max = 100, message = "{Size.user.email}")
    @Email(message = "{Email.user.email}")
    @ExistsByEmail
    private String email;

    @Column(name = "phone_number", unique = true)
    @NotEmpty(message = "{NotEmpty.user.phoneNumber}")
    @Size(min = 9, max = 20, message = "{Size.user.phoneNumber}")
    @Pattern(regexp = "^[0-9]{9,20}$", message = "{Pattern.user.phoneNumber}")
    @ExistsByPhoneNumber
    private String phoneNumber;

    @Column(unique = true)
    @Size(min = 1, max = 50, message = "{Size.user.username}")
    @NotBlank(message = "{NotBlank.user.username}")
    @ExistsByUsername
    private String username;

    @Column(name = "password_hash")
    @NotBlank(message = "{NotBlank.user.password}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "{NotNull.user.dob}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotBlank(message = "{NotBlank.user.address}")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    /**
     * Relationship
     */
    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "user_id", "role_id" }) })
    @JsonIgnoreProperties({ "users", "handler", "hibernateLazyInitializer" })
    private List<Role> roles;

    /**
     * Auxiliary
     */
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    /**
     * Embeddable
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
