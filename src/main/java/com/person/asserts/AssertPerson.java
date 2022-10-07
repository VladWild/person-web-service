package com.person.asserts;

import com.person.exceptions.NotFoundException;
import org.apache.commons.lang3.BooleanUtils;

public class AssertPerson {

    private AssertPerson() {
    }

    public static void notFoundTrue(String message, Object... args) {
        throw new NotFoundException(String.format(message, args));
    }

    public static void notFoundTrue(Boolean expression, String message) {
        if (BooleanUtils.isTrue(expression)) {
            throw new NotFoundException(message);
        }
    }

    public static void notFoundTrue(Boolean expression, String message, Object... args) {
        if (BooleanUtils.isTrue(expression)) {
            throw new NotFoundException(String.format(message, args));
        }
    }
}
