package com.cryfirock.msvc.users.msvc_users.validations;

import com.cryfirock.msvc.users.msvc_users.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByPhoneNumberValidator implements ConstraintValidator<ExistsByPhoneNumber, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return !userService.existsByPhoneNumber(phoneNumber);
    }

}
