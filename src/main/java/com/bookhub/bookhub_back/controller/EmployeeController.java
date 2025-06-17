package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.common.enums.Status;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeOrganizationUpdateRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpApprovalRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeDetailResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeListResponseDto;
import com.bookhub.bookhub_back.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API + ApiMappingPattern.ADMIN_API + "/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    // 1. 직원 검색 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<EmployeeListResponseDto>>> searchEmployee(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String branchName,
        @RequestParam(required = false) String positionName,
        @RequestParam(required = false) String authorityName,
        @RequestParam(required = false) Status status
    ) {
        ResponseDto<List<EmployeeListResponseDto>> responseDto = employeeService.searchEmployee(name, branchName, positionName, authorityName, status);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    // 2. 직원 상세 조회
    @GetMapping("/{employeeId}")
    public ResponseEntity<ResponseDto<EmployeeDetailResponseDto>> getEmployeeById(@PathVariable Long employeeId) {
        ResponseDto<EmployeeDetailResponseDto> responseDto = employeeService.getEmployeeById(employeeId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }


    @PutMapping("/{employeeId}/approve")
    public ResponseEntity<ResponseDto<EmployeeListResponseDto>> updateApproval(
        @PathVariable Long employeeId,
        @Valid @RequestBody EmployeeSignUpApprovalRequestDto dto,
        @AuthenticationPrincipal String loginId
    ) {
        ResponseDto<EmployeeListResponseDto> responseDto = employeeService.updateApproval(employeeId, dto, loginId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }

    @PutMapping("/{employeeId}/organization")
    public ResponseEntity<ResponseDto<Void>> updateOrganization(
        @PathVariable Long employeeId,
        @RequestBody EmployeeOrganizationUpdateRequestDto dto,
        @AuthenticationPrincipal String loginId
    ) {
        ResponseDto<Void> responseDto = employeeService.updateOrganization(employeeId, dto, loginId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, responseDto);
    }
}
