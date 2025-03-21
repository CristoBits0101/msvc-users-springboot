package com.cryfirock.msvc.users.msvc_users.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.msvc.users.msvc_users.entities.User;
import com.cryfirock.msvc.users.msvc_users.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Writing methods
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors())
            return validation(result);
        user.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PostMapping("/superuser")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors())
            return validation(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors())
            return validation(result);
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            user.setId(id);
            return ResponseEntity.ok(userService.save(user));
        }
        return ResponseEntity.notFound().build();
    }

    // Reading methods
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // Find user by id
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent())
            // Return 200 OK if found, else throws an exception (possible 500)
            return ResponseEntity.ok(userOptional.orElseThrow());

        // Return 404 Not Found if user does not exist
        return ResponseEntity.notFound().build();
    }

    // Removal methods
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // Create user object with id
        User user = new User();
        user.setId(id);

        // Delete user if found, else return 404 Not Found
        Optional<User> userOptional = userService.deleteUser(user);

        // Return 200 OK if found, else throws an exception (possible 500)
        if (userOptional.isPresent())
            return ResponseEntity.ok(userOptional.orElseThrow());

        // Return 404 Not Found if user does not exist
        return ResponseEntity.notFound().build();
    }

    // BindingResult returns annotation messages, not exceptions
    private ResponseEntity<?> validation(BindingResult result) {
        // Create a new user object to return errors
        Map<String, String> errors = new HashMap<>();

        // Loop through the validation errors and add them to the errors map
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        // Return 400 Bad Request with errors always after validation
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
