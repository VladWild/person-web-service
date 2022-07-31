package com.person.web.mapper;

import com.person.db.model.Person;
import com.person.web.dto.request.PersonRequestDto;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    Person mapToPerson(PersonRequestDto dto);
}
