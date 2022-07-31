package com.person.web.covertor;

import com.person.db.model.Person;
import com.person.web.dto.response.PersonResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonToPersonResponseDto implements Converter<Person, PersonResponseDto> {

    @Override
    public PersonResponseDto convert(Person person) {
        PersonResponseDto personDto = new PersonResponseDto();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        personDto.setAge(person.getAge());
        personDto.setRussian(person.getRussian());
        return personDto;
    }
}
