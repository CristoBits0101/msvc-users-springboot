package com.cryfirock.msvc.users.msvc_users.validations;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class ExistsByPhoneNumberValidation implements ConstraintValidator<ExistsByPhoneNumber, String> {

    /**
     * Attributes
     */
    @Autowired
    private UserService userService;

    /**
     * Validates if the phone number already exists in the database
     * 
     * @param value   The phone number value to validate
     * @param context The validation context
     * @return true if the phone number does not exist, false if it does
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.existsByPhoneNumber(value);
    }

}
