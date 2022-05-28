package com.person.web.logs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Order(0)
@Component
public class LoggerAudit {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("(@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping))")
    public void controllers() {
    }

    @Before("controllers()")
    public void advice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();

        Map<String, Object> argsWithValues = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i++) {
            String parameterName = parameterNames[i];
            Object o = args[i];
            argsWithValues.put(parameterName, o);
        }

        String requestArgsWithValues = argsWithValues.entrySet().stream()
                .map(e -> "Param: \"" + e.getKey() + "\", value: " + logInfo(e.getValue()))
                .collect(Collectors.joining("\n"));
        logger.info("Request arguments: {}", requestArgsWithValues);
    }

    @AfterReturning(value = "controllers()", returning = "response")
    public void logOkResponse(Object response) {
        logger.info("Response Payload: {} ", logInfo(response));
    }

    private String logInfo(Object o) {
        if (o == null) {
            return "null";
        }
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error("Error: {}", e.getMessage(), e);
        }

        return o.toString();
    }
}
