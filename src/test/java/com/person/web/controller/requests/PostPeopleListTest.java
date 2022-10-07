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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static com.person.web.controller.utils.TestControllerUtils.readRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        //mapper
        PersonMapperImpl.class,
        //service
        PersonService.class,
        //repository
        PersonRepository.class
})
@DisplayName("Сервис сохранения списка людей")
class PostPeopleListTest extends PersonControllerTest {
    private static final String POST_PERSON_V1_URL = PERSON_V1_URL + "/list";

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("1. Проверяем сохранение списка людей")
    void getIntervalsTest() throws Exception {
        MockHttpServletRequestBuilder request = post(POST_PERSON_V1_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(readRequest("post/json/data", "people.json"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(1))
                .andExpect(jsonPath("$[1]").value(2))
                .andExpect(jsonPath("$[2]").value(3));

        List<Person> people = personRepository.findAll();

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, people.size()),
                //Андрей
                () -> Assertions.assertEquals(1, people.get(0).getId()),
                () -> Assertions.assertEquals("Андрей", people.get(0).getName()),
                () -> Assertions.assertEquals(28, people.get(0).getAge()),
                () -> Assertions.assertEquals(true, people.get(0).getRussian()),
                //Константин
                () -> Assertions.assertEquals(2, people.get(1).getId()),
                () -> Assertions.assertEquals("Константин", people.get(1).getName()),
                () -> Assertions.assertEquals(24, people.get(1).getAge()),
                () -> Assertions.assertEquals(true, people.get(1).getRussian()),
                //Alex
                () -> Assertions.assertEquals(3, people.get(2).getId()),
                () -> Assertions.assertEquals("Alex", people.get(2).getName()),
                () -> Assertions.assertEquals(21, people.get(2).getAge()),
                () -> Assertions.assertEquals(false, people.get(2).getRussian())
        );
    }
}
