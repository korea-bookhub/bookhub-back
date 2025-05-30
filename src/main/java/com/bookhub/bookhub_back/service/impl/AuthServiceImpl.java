package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignInRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignInResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignUpResponseDto;
import com.bookhub.bookhub_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseDto<EmployeeSignUpResponseDto> signup(EmployeeSignUpRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<EmployeeSignInResponseDto> login(EmployeeSignInRequestDto dto) {
        return null;
    }
}