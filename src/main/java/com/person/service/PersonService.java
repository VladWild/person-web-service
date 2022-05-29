package com.person.service;

import com.person.db.model.Person;
import com.person.db.repository.PersonRepository;
import com.person.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

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

    public void changePerson(Long personId, Person person) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isEmpty()) {
            throw new NotFoundException(String.format("person %s not found", personId));
        }
        Person personDb = optionalPerson.get();
        BeanUtils.copyProperties(person, personDb, "id");
        personRepository.save(personDb);
    }

    public void deletePerson(Long personId) {
        if (personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
            return;
        }
        throw new NotFoundException(String.format("person with id = %s not found for delete", personId));
    }
}
