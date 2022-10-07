package com.person.web.controller.requests;

import com.person.db.model.Person;
import com.person.db.repository.PersonRepository;
import com.person.service.PersonService;
import com.person.web.controller.PersonControllerTest;
import com.person.web.mapper.PersonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        PersonService.class
})
@MockBean(classes = {
        PersonMapper.class
})
@DisplayName("DELETE Сервис удаления человека по id")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeletePeopleTest extends PersonControllerTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @Order(1)
    @Sql("classpath:delete/sql/data/insert_people_for_delete.sql")
    @DisplayName("1. Проверяем корректное удаление человека из базы")
    void deletePersonTest() throws Exception {
        MockHttpServletRequestBuilder request = delete(PERSON_V1_URL + "/1");

        mockMvc.perform(request)
                .andExpect(status().isOk());

        Optional<Person> personOptional = personRepository.findById(1L);

        Assertions.assertTrue(personOptional.isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("2. Ошибка при удалении не существующего человека")
    void deletePersonTest2() throws Exception {
        int personId = 2;

        MockHttpServletRequestBuilder request = delete(PERSON_V1_URL + "/" + personId);

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reasons")
                        .value(String.format("person with id = %s not found for delete", personId)));
    }
}
