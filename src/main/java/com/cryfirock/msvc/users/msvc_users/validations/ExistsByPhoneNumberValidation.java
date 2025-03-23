package com.cryfirock.msvc.users.msvc_users.validations;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class ExistsByPhoneNumberValidation implements ConstraintValidator<ExistsByPhoneNumber, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.existsByPhoneNumber(value);
    }

}
