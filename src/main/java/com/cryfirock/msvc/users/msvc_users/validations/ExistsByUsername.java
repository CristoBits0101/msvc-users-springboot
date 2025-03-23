package com.cryfirock.msvc.users.msvc_users.validations;

/**
 * Dependencies
 */
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Specifies the class that implements the validation logic
// Can only be applied to fields
// The annotation will be available at runtime
@Constraint(validatedBy = ExistsByUsernameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUsername {

    /**
     * Default error message when validation fails
     * 
     * @return Error message
     */
    String message() default "Username already exists.";

    /**
     * Groups for validation categorization
     * 
     * @return Groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload for additional metadata about the validation failure
     * 
     * @return Payload class
     */
    Class<? extends Payload>[] payload() default {};
    
}
