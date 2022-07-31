package com.person.web.validator.impl;

import com.person.web.dto.request.PersonRequestDto;
import com.person.web.validator.PersonValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Гражданин не из России должен быть старше 17-ти лет
 */
public class PersonValidator implements ConstraintValidator<PersonValid, PersonRequestDto> {
    private static final String ERROR_FOR_NOT_RUSSIAN = "not russian must be more %d age";
    private static final int MIN_AGE_FOR_NOT_RUSSIAN = 17;

    @Override
    public boolean isValid(PersonRequestDto personRequestDto, ConstraintValidatorContext context) {
        if (personRequestDto == null || isRussian(personRequestDto.getRussian())) {
            return true;
        }

        if (!isRussian(personRequestDto.getRussian()) && personRequestDto.getAge() <= MIN_AGE_FOR_NOT_RUSSIAN) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format(ERROR_FOR_NOT_RUSSIAN, MIN_AGE_FOR_NOT_RUSSIAN))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isRussian(Boolean russian) {
        return Boolean.TRUE.equals(russian);
    }
}
