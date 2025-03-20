package com.cryfirock.msvc.users.msvc_users.validations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// It runs at runtime
@Retention(RetentionPolicy.RUNTIME)
// It can be used in fields and methods
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface IsRequired {
    
}
