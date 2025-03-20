package com.cryfirock.msvc.users.msvc_users.entities;

import java.time.LocalDate;
import java.util.List;

import com.cryfirock.msvc.users.msvc_users.models.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "{NotEmpty.user.firstName}")
    @Size(min = 1, max = 50, message = "{Size.user.firstName}")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "{NotEmpty.user.lastName}")
    @Size(min = 1, max = 50, message = "{Size.user.lastName}")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "{NotBlank.user.email}")
    @Size(min = 1, max = 100, message = "{Size.user.email}")
    private String email;

    @Column(name = "phone_number", unique = true)
    @NotEmpty(message = "{NotEmpty.user.phoneNumber}")
    @Size(min = 9, max = 20, message = "{Size.user.phoneNumber}")
    private String phoneNumber;

    @Column(unique = true)
    @Size(min = 1, max = 50, message = "{Size.user.username}")
    private String username;

    @Column(name = "password_hash")
    @NotBlank(message = "{NotBlank.user.password}")
    private String password;

    @NotNull(message = "{NotNull.user.dob}")
    private LocalDate dob;

    @NotBlank(message = "{NotBlank.user.address}")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    @NotNull(message = "{NotNull.user.accountStatus}")
    private AccountStatus accountStatus;

    // One-way relationship: Roles can only be obtained from users
    @ManyToMany
    @JoinTable(
            // Link the intermediate table of ManyToMany relationships
            name = "users_roles",
            // Column that stores the foreign keys of the users table
            joinColumns = @JoinColumn(name = "user_id"),
            // Column that stores the foreign keys of the roles table
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            // Unique constraint to prevent duplicate entries
            uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "role_id" }) })
    private List<Role> roles;

    // It does not belong to the database
    @Transient
    private boolean admin;

    // Constructors
    public User() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    // When it is boolean, use is and not get
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
