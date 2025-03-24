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
public class ExistsByEmailValidation implements ConstraintValidator<ExistsByEmail, String> {

    /**
     * Attributes
     */
    @Autowired
    private UserService userService;

    /**
     * Validates if the email already exists in the database
     * 
     * @param value   The email value to validate
     * @param context The validation context
     * @return true if the email does not exist, false if it does
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.existsByEmail(value);
    }

}
