package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.SignInRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.SignUpRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.SignInResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.SignUpResponseDto;
import com.bookhub.bookhub_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseDto<SignUpResponseDto> signup(SignUpRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<SignInResponseDto> login(SignInRequestDto dto) {
        return null;
    }
}