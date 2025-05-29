package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.SignInRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.SignUpRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.SignInResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.SignUpResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    ResponseDto<SignUpResponseDto> signup(@Valid SignUpRequestDto dto);
    ResponseDto<SignInResponseDto> login(@Valid SignInRequestDto dto);
}
