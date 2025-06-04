package com.bookhub.bookhub_back.dto.author.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthorRequestDto {
    @NotNull
    private String authorName;
    @NotNull
    private String authorEmail;
}
