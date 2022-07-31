package com.person.web.validator;

import com.person.web.validator.impl.PersonValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersonValidator.class)
public @interface PersonValid {
    String message() default "error person data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
