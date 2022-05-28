package com.person.web.covertor;

import com.person.db.model.Person;
import com.person.web.dto.PersonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonToPersonDto implements Converter<Person, PersonDto> {

    @Override
    public PersonDto convert(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setName(person.getName());
        personDto.setAge(person.getAge());
        personDto.setRussian(person.getRussian());
        return personDto;
    }
}
