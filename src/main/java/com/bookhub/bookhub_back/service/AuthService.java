package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignInRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignInResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignUpResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    ResponseDto<EmployeeSignUpResponseDto> signup(@Valid EmployeeSignUpRequestDto dto);
    ResponseDto<EmployeeSignInResponseDto> login(@Valid EmployeeSignInRequestDto dto);
}
