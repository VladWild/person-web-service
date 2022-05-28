package com.person.service;

import com.person.db.model.Person;
import com.person.db.repository.PersonRepository;
import com.person.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Long savePerson(Person person) {
        Person personSave = personRepository.save(person);
        logger.info("Person Save: {}", personSave);
        return personSave.getId();
    }

    public List<Person> getPeople(boolean russian) {
        List<Person> people = russian ? personRepository.findOnlyRussian() : personRepository.findAll();
        if (CollectionUtils.isEmpty(people)) {
            throw new NotFoundException("people not found");
        }
        return people;
    }
}
