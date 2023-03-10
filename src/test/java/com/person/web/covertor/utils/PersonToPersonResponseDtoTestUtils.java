package com.person.web.covertor.utils;

import com.person.db.model.Person;

public class PersonToPersonResponseDtoTestUtils {

    private PersonToPersonResponseDtoTestUtils() {
    }

    public static Person getPerson(Long id, String name, int age, boolean russian) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setAge(age);
        person.setRussian(russian);
        return person;
    }
}
