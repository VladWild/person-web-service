package com.person.web.dto.response;

import com.person.web.dto.request.PersonRequestDto;

public class PersonResponseDto extends PersonRequestDto {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
