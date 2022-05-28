package com.person.web.mapper;

import com.person.db.model.Person;
import com.person.web.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    Person mapToPerson(PersonDto dto);
}
