package com.person.web.controller.requests;

import com.person.service.PersonService;
import com.person.web.controller.PersonControllerTest;
import com.person.web.covertor.PersonToPersonResponseDto;
import com.person.web.mapper.PersonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        //convertor
        PersonToPersonResponseDto.class,
        //service
        PersonService.class
})
@MockBean(classes = {
        PersonMapper.class
})
@DisplayName("Сервис получения списка людей")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GetPeopleTest extends PersonControllerTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        addConversionService(PersonToPersonResponseDto.class);
    }

    @Test
    @Order(1)
    @Sql("classpath:get/sql/data/insert_people.sql")
    @DisplayName("1. Проверяем наличие всех людей")
    void getIntervalsTest() throws Exception {
        MockHttpServletRequestBuilder request = get(PERSON_V1_URL);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Андрей"))
                .andExpect(jsonPath("$[0].age").value(28))
                .andExpect(jsonPath("$[0].russian").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Константин"))
                .andExpect(jsonPath("$[1].age").value(24))
                .andExpect(jsonPath("$[1].russian").value(true))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Alex"))
                .andExpect(jsonPath("$[2].age").value(21))
                .andExpect(jsonPath("$[2].russian").value(false))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].name").value("Sam"))
                .andExpect(jsonPath("$[3].age").value(29))
                .andExpect(jsonPath("$[3].russian").value(false))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[4].name").value("Николай"))
                .andExpect(jsonPath("$[4].age").value(24))
                .andExpect(jsonPath("$[4].russian").value(true));
    }

    @Test
    @Order(2)
    @Sql("classpath:get/sql/data/insert_people.sql")
    @DisplayName("2. Проверяем наличие людей только из России")
    void getIntervalsTest2() throws Exception {
        MockHttpServletRequestBuilder request = get(PERSON_V1_URL)
                .param("russian", "true");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Андрей"))
                .andExpect(jsonPath("$[0].age").value(28))
                .andExpect(jsonPath("$[0].russian").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Константин"))
                .andExpect(jsonPath("$[1].age").value(24))
                .andExpect(jsonPath("$[1].russian").value(true))
                .andExpect(jsonPath("$[2].id").value(5))
                .andExpect(jsonPath("$[2].name").value("Николай"))
                .andExpect(jsonPath("$[2].age").value(24))
                .andExpect(jsonPath("$[2].russian").value(true));
    }

    @Test
    @Order(3)
    @DisplayName("3. Ошибочный респонс отсутствия людей")
    void getIntervalsTest3() throws Exception {
        MockHttpServletRequestBuilder request = get(PERSON_V1_URL);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound());
    }
}
