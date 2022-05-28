package com.person.web.controller;

import com.person.db.model.Person;
import com.person.service.PersonService;
import com.person.web.dto.PersonDto;
import com.person.web.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "/api/v1/person",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ConversionService conversionService;
    private final PersonService personService;
    private final PersonMapper personMapper;

    public PersonController(ConversionService conversionService,
                            PersonService personService,
                            PersonMapper personMapper) {
        this.conversionService = conversionService;
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("/list")
    public List<PersonDto> getPeople(@RequestParam(required = false) Boolean russian) {
        List<Person> people = personService.getPeople(Boolean.TRUE.equals(russian));
        return people.stream()
                .map(person -> conversionService.convert(person, PersonDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long savePerson(@RequestBody @Valid PersonDto dto) {
        logger.info("PersonDto: {}", dto);
        Person person = personMapper.mapToPerson(dto);
        return personService.savePerson(person);
    }
}
