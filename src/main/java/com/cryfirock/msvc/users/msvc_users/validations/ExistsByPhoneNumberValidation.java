package com.cryfirock.msvc.users.msvc_users.validations;

import com.cryfirock.msvc.users.msvc_users.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsByPhoneNumberValidation implements ConstraintValidator<ExistsByPhoneNumber, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.existsByPhoneNumber(value);
    }

}
