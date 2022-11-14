package com.person.service.utils;

import com.person.db.model.Person;

import java.util.Arrays;
import java.util.List;

/**
 * Всякие подготовительные данные для моков лучше
 * вот так в тксты выносить
 */
public class PersonServiceTestUtils {

    private PersonServiceTestUtils() {}

    public static List<Person> getPeople() {
        return Arrays.asList(
                getPerson(1L, "Николай", 26, true),
                getPerson(2L, "Екатерина", 23, true),
                getPerson(3L, "Alex", 31, false),
                getPerson(4L, "Kate", 28, false)
        );
    }

    public static List<Person> getRussianPeople() {
        return getPeople()
                .stream()
                .filter(Person::getRussian)
                .toList();
    }

    public static List<Person> getPeopleWithoutIds() {
        return getPeople()
                .stream()
                .peek(person -> person.setId(null))
                .toList();
    }

    private static Person getPerson(Long id, String name, Integer age, Boolean russian) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setAge(age);
        person.setRussian(russian);
        return person;
    }
}
