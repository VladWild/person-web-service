package com.person.service;

import com.person.db.model.Person;
import com.person.db.repository.PersonRepository;
import com.person.exceptions.NotFoundException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.person.service.utils.PersonServiceTestUtils.getPeople;
import static com.person.service.utils.PersonServiceTestUtils.getPeopleWithoutIds;
import static com.person.service.utils.PersonServiceTestUtils.getRussianPeople;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Пример тестов типичных легковестных unit'ов (бд не поднимается)
 * с данными, замокаными на базу
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonServiceTest {

    private PersonService personService;

    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);

        personService = new PersonService(personRepository);
    }

    /**
     * Общий план написания лекковестного unit-теста с моками:
     * 1) Подготавливаем данные для сервиса
     * 2) Мокаем необходимые данные
     * 3) Вызываем метод у сервиса
     * 4) Проверяем данные, которые пришли
     */

    @Test
    @Order(1)
    @DisplayName("1. Получение всех людей из базы")
    void getPeopleTest() {
        boolean russian = false;

        when(personRepository.findAll()).thenReturn(getPeople());

        List<Person> people = personService.getPeople(russian);

        Assertions.assertAll(
                () -> Assertions.assertEquals(4, people.size()),

                () -> Assertions.assertEquals(1, people.get(0).getId()),
                () -> Assertions.assertEquals("Николай", people.get(0).getName()),
                () -> Assertions.assertEquals(26, people.get(0).getAge()),
                () -> Assertions.assertEquals(true, people.get(0).getRussian()),

                () -> Assertions.assertEquals(2, people.get(1).getId()),
                () -> Assertions.assertEquals("Екатерина", people.get(1).getName()),
                () -> Assertions.assertEquals(23, people.get(1).getAge()),
                () -> Assertions.assertEquals(true, people.get(1).getRussian()),

                () -> Assertions.assertEquals(3, people.get(2).getId()),
                () -> Assertions.assertEquals("Alex", people.get(2).getName()),
                () -> Assertions.assertEquals(31, people.get(2).getAge()),
                () -> Assertions.assertEquals(false, people.get(2).getRussian()),

                () -> Assertions.assertEquals(4, people.get(3).getId()),
                () -> Assertions.assertEquals("Kate", people.get(3).getName()),
                () -> Assertions.assertEquals(28, people.get(3).getAge()),
                () -> Assertions.assertEquals(false, people.get(3).getRussian())
        );
    }

    @Test
    @Order(2)
    @DisplayName("2. Получение людей из базы только из России")
    void getPeopleTest2() {
        boolean russian = true;

        when(personRepository.findOnlyRussian()).thenReturn(getRussianPeople());

        List<Person> people = personService.getPeople(russian);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, people.size()),

                () -> Assertions.assertEquals(1, people.get(0).getId()),
                () -> Assertions.assertEquals("Николай", people.get(0).getName()),
                () -> Assertions.assertEquals(26, people.get(0).getAge()),
                () -> Assertions.assertEquals(true, people.get(0).getRussian()),

                () -> Assertions.assertEquals(2, people.get(1).getId()),
                () -> Assertions.assertEquals("Екатерина", people.get(1).getName()),
                () -> Assertions.assertEquals(23, people.get(1).getAge()),
                () -> Assertions.assertEquals(true, people.get(1).getRussian())
        );
    }

    @Order(3)
    @ParameterizedTest(name = "{index}. Люди только из России: \"{arguments}\"")
    @ValueSource(booleans = {true, false})
    @DisplayName("3. Ошибка получения людей, так как не найдены")
    void getPeopleTest3(boolean russian) {
        when(personRepository.findOnlyRussian()).thenReturn(Collections.emptyList());
        when(personRepository.findAll()).thenReturn(Collections.emptyList());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> personService.getPeople(russian));
        Assertions.assertEquals("people not found", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("4. Сохранение людей")
    void savePersonsTest() {
        List<Person> peopleWithoutIds = getPeopleWithoutIds();

        Mockito.doAnswer((Answer<List<Person>>) answer -> {
            List<Person> people = answer.getArgument(0);
            AtomicLong atomicLong = new AtomicLong(0);
            people.forEach(person -> person.setId(atomicLong.incrementAndGet()));
            return people;
        }).when(personRepository).saveAll(anyList());

        Long[] personIds = personService.savePersons(peopleWithoutIds);

        Assertions.assertArrayEquals(new long[]{1L, 2L, 3L, 4L}, ArrayUtils.toPrimitive(personIds));
    }
}