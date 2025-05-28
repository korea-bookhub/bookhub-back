package com.project.board_back.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSignInRequestDto {

    @NotBlank(message = "아이디는 필수 항목입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;
}