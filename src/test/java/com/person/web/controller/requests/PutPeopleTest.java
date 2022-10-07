package com.person.web.controller.requests;

import com.person.db.model.Person;
import com.person.db.repository.PersonRepository;
import com.person.service.PersonService;
import com.person.web.controller.PersonControllerTest;
import com.person.web.mapper.PersonMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static com.person.web.controller.utils.TestControllerUtils.readRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        //mapper
        PersonMapperImpl.class,
        //service
        PersonService.class,
        //repository
        PersonRepository.class
})
@DisplayName("PUT Сервис изменения человека")
class PutPeopleTest extends PersonControllerTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Sql("classpath:put/sql/data/insert_person_for_change.sql")
    @DisplayName("1. Проверяем изменение человека на данные из json'а")
    void getIntervalsTest() throws Exception {
        MockHttpServletRequestBuilder request = put(PERSON_V1_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readRequest("put/json/data", "person-for-change.json"));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        Optional<Person> optionalPerson = personRepository.findById(1L);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Alex", optionalPerson.get().getName()),
                () -> Assertions.assertEquals(21, optionalPerson.get().getAge()),
                () -> Assertions.assertEquals(false, optionalPerson.get().getRussian())
        );
    }
}
