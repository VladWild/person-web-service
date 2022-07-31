package com.person.web.validator.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class ValidatorTestUtils {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidatorTestUtils() {
    }

    public static <T> boolean dtoHasErrorMessage(T dto, @NotNull String message) {
        Set<ConstraintViolation<T>> errors = VALIDATOR.validate(dto);
        return errors.stream().map(ConstraintViolation::getMessage).anyMatch(message::equals);
    }
}
