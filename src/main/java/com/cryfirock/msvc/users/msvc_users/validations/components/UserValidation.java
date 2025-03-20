package com.cryfirock.msvc.users.msvc_users.validations.components;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
// import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cryfirock.msvc.users.msvc_users.entities.User;

@Component
public class UserValidation implements Validator {

    // Configuration for validation of User entity
    @SuppressWarnings("null")
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    /**
     * Validation of User entity
     *
     * @param target Object that represents the entity to be validated
     * @param errors Errors that represent the errors BindingResult result
     */
    @SuppressWarnings("null")
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        /**
         * Alternative 2 to validate
         */

        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.user.firstName");

        /**
         * Alternative 2 to validate
         */
        
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", null, "The name is required!");

        /**
         * Alternative 3 to validate
         */

        // Validate First Name
        if (user.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "NotEmpty.user.firstName");
        } else if (user.getFirstName().length() > 50) {
            errors.rejectValue("firstName", "Size.user.firstName");
        }

        // Validate Last Name
        if (user.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "NotEmpty.user.lastName");
        } else if (user.getLastName().length() > 50) {
            errors.rejectValue("lastName", "Size.user.lastName");
        }

        // Validate Email
        if (user.getEmail().isEmpty()) {
            errors.rejectValue("email", "NotBlank.user.email");
        } else if (user.getEmail().length() > 100) {
            errors.rejectValue("email", "Size.user.email");
        } else if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            // Invalid email format
            errors.rejectValue("email", "Pattern.user.email");
        }

        // Validate Phone Number
        if (user.getPhoneNumber().isEmpty()) {
            errors.rejectValue("phoneNumber", "NotEmpty.user.phoneNumber");
        } else if (user.getPhoneNumber().length() < 9 || user.getPhoneNumber().length() > 20) {
            errors.rejectValue("phoneNumber", "Size.user.phoneNumber");
        } else if (!user.getPhoneNumber().matches("^[0-9]+$")) {
            // Invalid format
            errors.rejectValue("phoneNumber", "Pattern.user.phoneNumber");
        }

        // Validate Username
        if (user.getUsername().length() > 50) {
            errors.rejectValue("username", "Size.user.username");
        }

        // Validate Password
        if (user.getPassword().isEmpty()) {
            errors.rejectValue("password", "NotBlank.user.password");
        } else if (user.getPassword().length() < 8) {
            // Minimum length requirement
            errors.rejectValue("password", "Size.user.password");
        } else if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$")) {
            // Must include uppercase, lowercase, number and special character
            errors.rejectValue("password", "Pattern.user.password");
        }

        // Validate Date of Birth (dob)
        if (user.getDob() == null) {
            errors.rejectValue("dob", "NotNull.user.dob");
        } else if (user.getDob().isAfter(LocalDate.now())) {
            // Date of birth cannot be in the future
            errors.rejectValue("dob", "Future.user.dob");
        } else if (user.getDob().isAfter(LocalDate.now().minusYears(18))) {
            // Must be at least 18 years old
            errors.rejectValue("dob", "Age.user.dob");
        }

        // Validate Address
        if (user.getAddress().isEmpty()) {
            errors.rejectValue("address", "NotBlank.user.address");
        }

        // Validate Account Status
        if (user.getAccountStatus() == null) {
            errors.rejectValue("accountStatus", "NotNull.user.accountStatus");
        }
    }
}
