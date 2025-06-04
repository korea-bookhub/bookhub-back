package com.bookhub.bookhub_back.dto.author.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class AuthorCreateRequestDto {
    private List<AuthorRequestDto> authors;

}
