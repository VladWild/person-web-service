package com.person.web.dto.request;

import com.person.web.validator.PersonValid;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@PersonValid
public class PersonRequestDto {
    @NotBlank(message = "name should not be blank")
    private String name;
    @NotNull(message = "age should not be null")
    @Min(value = 0, message = "age should be greater than 0")
    private Integer age;
    private Boolean russian;
    private LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 12, 0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getRussian() {
        return russian;
    }

    public void setRussian(Boolean russian) {
        this.russian = russian;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", russian=" + russian +
                '}';
    }
}
