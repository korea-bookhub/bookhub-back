package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.common.enums.Status;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpApprovalRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeListResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface EmployeeService {
    ResponseDto<List<EmployeeListResponseDto>> searchEmployee(String name, String branchName, String positionName, String authorityName, Status status);

    ResponseDto<EmployeeListResponseDto> updateApproval(Long id, @Valid EmployeeSignUpApprovalRequestDto dto, String email);
}
