package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.auth.request.UserSignInRequestDto;
import com.bookhub.bookhub_back.dto.auth.request.UserSignUpRequestDto;
import com.bookhub.bookhub_back.dto.auth.response.UserSignInResponseDto;
import com.bookhub.bookhub_back.dto.auth.response.UserSignUpResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    ResponseDto<UserSignUpResponseDto> signup(@Valid UserSignUpRequestDto dto);
    ResponseDto<UserSignInResponseDto> login(@Valid UserSignInRequestDto dto);
}
