package com.person.web.covertor;

import com.person.db.model.Person;
import com.person.web.dto.response.PersonResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.person.web.covertor.utils.PersonToPersonResponseDtoTestUtils.getPerson;

class PersonToPersonResponseDtoTest {

    private PersonToPersonResponseDto converter = new PersonToPersonResponseDto();

    @ParameterizedTest(name = "№ {index}. id = {0}, имя = {1}, возраст = {2}, из России = {3}")
    @MethodSource("com.person.web.covertor.arguments.PersonToPersonResponseDtoTestArgs#getArguments")
    @DisplayName("Конвертация person'а")
    void convertTest(Long id, String name, int age, boolean russian) {
        Person person = getPerson(id, name, age, russian);
        PersonResponseDto responseDto = converter.convert(person);
        Assertions.assertAll(
                () -> Assertions.assertEquals(responseDto.getId(), id),
                () -> Assertions.assertEquals(responseDto.getName(), name),
                () -> Assertions.assertEquals(responseDto.getAge(), age),
                () -> Assertions.assertEquals(responseDto.getRussian(), russian)
        );
    }
}