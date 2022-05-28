package com.person.db.repository;

import com.person.db.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.russian = true")
    List<Person> findOnlyRussian();
}
