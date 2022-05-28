package com.person.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.person")
@EnableJpaRepositories(basePackages = "com.person.db.repository")
@EntityScan(basePackages = "com.person.db.model")
public class ApplicationPersonStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationPersonStarter.class, args);
    }
}
