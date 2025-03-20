package com.cryfirock.msvc.users.msvc_users.validations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// It validates the IsRequired annotation
@Constraint(validatedBy = RequiredValidation.class)
// It runs at runtime
@Retention(RetentionPolicy.RUNTIME)
// It can be used in fields and methods
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface IsRequired {

    String message() default "This field is required.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
