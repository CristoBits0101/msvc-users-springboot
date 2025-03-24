package com.cryfirock.msvc.users.msvc_users.validations;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    /**
     * Attributes
     */
    @Autowired
    private UserService userService;

    /**
     * Validates if the username already exists in the database
     * 
     * @param value   The username value to validate
     * @param context The validation context
     * @return true if the username does not exist, false if it does
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userService == null)
            return true;
            
        return !userService.existsByUsername(value);
    }

}
