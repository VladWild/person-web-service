package com.person.service;

import com.person.asserts.AssertPerson;
import com.person.db.model.Person;
import com.person.db.repository.PersonRepository;
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

    public Long[] savePersons(List<Person> people) {
        List<Person> peopleSave = personRepository.saveAll(people);
        logger.info("People Save: {}", peopleSave);
        return peopleSave.stream().map(Person::getId).toArray(Long[]::new);
    }

    public List<Person> getPeople(boolean russian) {
        List<Person> people = russian ? personRepository.findOnlyRussian() : personRepository.findAll();
        AssertPerson.notFoundTrue(CollectionUtils.isEmpty(people), "people not found");
        return people;
    }

    public void changePerson(Long personId, Person person) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        AssertPerson.notFoundTrue(optionalPerson.isEmpty(), "person %s not found", personId);
        @SuppressWarnings("all")
        Person personDb = optionalPerson.get();
        BeanUtils.copyProperties(person, personDb, "id");
        personRepository.save(personDb);
    }

    public void deletePerson(Long personId) {
        if (personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
            return;
        }
        AssertPerson.notFoundTrue("person with id = %s not found for delete", personId);
    }
}
