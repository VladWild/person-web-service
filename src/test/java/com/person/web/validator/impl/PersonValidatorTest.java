package com.person.web.validator.impl;

import com.person.web.dto.request.PersonRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.person.web.validator.impl.utils.PersonValidatorTestUtils.getPersonRequestDto;
import static com.person.web.validator.utils.ValidatorTestUtils.dtoHasErrorMessage;

/**
 * Примеры тестов (параметризированным тестов) на кастомный валидатор
 */
class PersonValidatorTest {

    /**
     * Пример обычного unit-теста с одним начальным значением
     */
    @Test
    @DisplayName("1) Проверка невалидности имени и возраста в dto со значениями null")
    void createPersonRequestDtoTest() {
        PersonRequestDto personRequestDto = getPersonRequestDto(null, null, true);
        Assertions.assertAll(
                () -> Assertions.assertTrue(dtoHasErrorMessage(personRequestDto, "name should not be blank")),
                () -> Assertions.assertTrue(dtoHasErrorMessage(personRequestDto, "age should not be null"))
        );
    }

    /**
     * Пример параметризированного unit-теста с несколькими начальными значениями типа String
     */
    @ParameterizedTest(name = "{index}. Проверка невалидности имени \"{arguments}\"")
    @ValueSource(strings = {"", " ", "  ", "   ", "    ", "     "})
    @DisplayName("2) Проверка невалидности имени в dto с пустыми значениями")
    void createPersonRequestDtoTest2(String name) {
        PersonRequestDto personRequestDto = getPersonRequestDto(name, 25, true);
        Assertions.assertTrue(dtoHasErrorMessage(personRequestDto, "name should not be blank"));
    }

    /**
     * Пример параметризированного unit-теста с несколькими начальными значениями типа int
     */
    @ParameterizedTest(name = "{index}. Проверка невалидности возраста \"{arguments}\"")
    @ValueSource(ints = {Integer.MIN_VALUE, -10, -5, -1})
    @DisplayName("3) Проверка невалидности возраста в dto")
    void createPersonRequestDtoTest3(int age) {
        PersonRequestDto personRequestDto = getPersonRequestDto("Игорь", age, true);
        Assertions.assertTrue(dtoHasErrorMessage(personRequestDto, "age should be greater than 0"));
    }

    @MethodSource("getPersonRequestDtos")
    @ParameterizedTest(name = "{index}. Данные не российского гражданина: {arguments} ")
    @DisplayName("4) Проверка невалидности не российских граждан")
    void createPersonRequestDtoTest4(PersonRequestDto requestDto) {
        Assertions.assertTrue(dtoHasErrorMessage(requestDto,
                String.format("not russian must be more 17 age", requestDto.getAge())));
    }

    private static Stream<PersonRequestDto> getPersonRequestDtos() {
        return Stream.of(
                getPersonRequestDto("Alex", 0, false),
                getPersonRequestDto("Alex", 10, false),
                getPersonRequestDto("Alex", 17, false),
                getPersonRequestDto("Alex", 0, null),
                getPersonRequestDto("Alex", 10, null),
                getPersonRequestDto("Alex", 17, null)
        );
    }

    @Test
    @DisplayName("5) Разные проверки невалидности данных")
    void createPersonRequestDtoTest5() {
        PersonRequestDto personRequestDto = getPersonRequestDto("     ", -2, null);
        Assertions.assertAll(
                () -> Assertions.assertTrue(dtoHasErrorMessage(personRequestDto, "name should not be blank")),
                () -> Assertions.assertTrue(dtoHasErrorMessage(personRequestDto, "not russian must be more 17 age")),
                () -> Assertions.assertTrue(dtoHasErrorMessage(personRequestDto, "age should be greater than 0"))
        );
    }
}