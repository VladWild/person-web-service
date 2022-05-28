package com.person.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.person")
public class ApplicationPersonStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationPersonStarter.class, args);
    }
}
