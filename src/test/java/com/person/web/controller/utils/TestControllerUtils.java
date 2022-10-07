package com.person.web.controller.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestControllerUtils {

    public static byte[] readRequest(String path, String fileName) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Path.of(String.format("src/test/resources/%s/%s", path, fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }
}
