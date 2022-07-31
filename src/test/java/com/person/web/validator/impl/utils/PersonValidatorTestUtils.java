package com.person.web.validator.impl.utils;

import com.person.web.dto.request.PersonRequestDto;

public class PersonValidatorTestUtils {

    public static PersonRequestDto getPersonRequestDto(String name, Integer age, Boolean russian) {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setName(name);
        personRequestDto.setAge(age);
        personRequestDto.setRussian(russian);
        return personRequestDto;
    }
}
