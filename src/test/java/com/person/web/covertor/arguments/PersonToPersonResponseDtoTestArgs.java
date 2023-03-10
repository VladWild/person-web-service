package com.person.web.covertor.arguments;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PersonToPersonResponseDtoTestArgs {

    private PersonToPersonResponseDtoTestArgs() {
    }

    public static List<Arguments> getArguments() {
        return List.of(
                arguments(1L, "Сергей", 22, true),
                arguments(2L, "Max", 29, false),
                arguments(3L, "Катя", 34, true),
                arguments(4L, "Sam", 25, false)
        );
    }
}
