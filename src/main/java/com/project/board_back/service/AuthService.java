package com.project.board_back.service;

import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.auth.request.UserSignInRequestDto;
import com.project.board_back.dto.auth.request.UserSignUpRequestDto;
import com.project.board_back.dto.auth.response.UserSignInResponseDto;
import com.project.board_back.dto.auth.response.UserSignUpResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    ResponseDto<UserSignUpResponseDto> signup(@Valid UserSignUpRequestDto dto);
    ResponseDto<UserSignInResponseDto> login(@Valid UserSignInRequestDto dto);
}
