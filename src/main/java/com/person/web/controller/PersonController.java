package com.person.web.controller;

import com.person.web.dto.PersonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/v1/person",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long savePerson(@RequestBody PersonDto dto) {
        logger.info("PersonDto: {}", dto);
        return null;
    }
}
