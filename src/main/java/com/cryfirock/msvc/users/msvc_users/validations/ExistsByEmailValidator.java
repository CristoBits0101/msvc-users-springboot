package com.cryfirock.msvc.users.msvc_users.validations;

import com.cryfirock.msvc.users.msvc_users.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByEmailValidator implements ConstraintValidator<ExistsByEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.existsByEmail(email);
    }

}
