package com.person.web.covertor;

import com.person.db.model.Person;
import com.person.web.dto.PersonDto;
import com.person.web.dto.PersonDtoResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonToPersonDtoResponse implements Converter<Person, PersonDtoResponse> {

    @Override
    public PersonDtoResponse convert(Person person) {
        PersonDtoResponse personDto = new PersonDtoResponse();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        personDto.setAge(person.getAge());
        personDto.setRussian(person.getRussian());
        return personDto;
    }
}
