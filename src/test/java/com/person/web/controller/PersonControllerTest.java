package com.person.web.controller;

import com.person.web.config.JpaH2Runner;
import com.person.web.handler.RestResponseEntityExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@EntityScan(basePackages = "com.person.db.model")
@EnableJpaRepositories(basePackages = "com.person.db.repository")
@ContextConfiguration(classes = {
        //controller
        PersonController.class,
        //handler
        RestResponseEntityExceptionHandler.class,
        //converters
        DefaultFormattingConversionService.class
})
public abstract class PersonControllerTest extends JpaH2Runner {
    protected static final String PERSON_V1_URL = "/api/v1/person";

    protected MockMvc mockMvc;

    @Autowired
    private PersonController controller;

    @Autowired
    private RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FormattingConversionService conversionService;

    @BeforeEach
    public void setUp() {
        applicationContext.getBeansOfType(Formatter.class).values().forEach(conversionService::addFormatter);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(restResponseEntityExceptionHandler)
                .setConversionService(conversionService)
                .build();
    }

    protected void addConversionService(Class<? extends Converter<?, ?>> service) {
        Converter<?, ?> bean = applicationContext.getBean(service);
        conversionService.addConverter(bean);
    }
}