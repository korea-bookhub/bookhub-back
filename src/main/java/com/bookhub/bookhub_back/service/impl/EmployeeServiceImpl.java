package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.common.enums.IsApproved;
import com.bookhub.bookhub_back.common.enums.Status;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpApprovalRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeListResponseDto;
import com.bookhub.bookhub_back.entity.Employee;
import com.bookhub.bookhub_back.entity.EmployeeSignUpApproval;
import com.bookhub.bookhub_back.repository.EmployeeRepository;
import com.bookhub.bookhub_back.repository.EmployeeSignUpApprovalRepository;
import com.bookhub.bookhub_back.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSignUpApprovalRepository employeeSignUpApprovalRepository;

    @Override
    public ResponseDto<List<EmployeeListResponseDto>> searchEmployee(
        String name,
        String branchName,
        String positionName,
        String authorityName,
        Status status
    ) {
        List<Employee> employees = null;
        List<EmployeeListResponseDto> responseDtos = null;

        employees = employeeRepository.searchEmployees(name,branchName, positionName, authorityName, status);


        responseDtos = employees.stream()
            .map(employee -> EmployeeListResponseDto.builder()
                .EmployeeNumber(employee.getEmployeeNumber())
                .EmployeeName(employee.getName())
                .branchName(employee.getBranchId().getBranchName())
                .positionName(employee.getPositionId().getPositionName())
                .authorityName(employee.getAuthorityId().getAuthorityName())
                .status(employee.getStatus())
                .build())
            .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<EmployeeListResponseDto> updateApproval(Long EmployeeId, EmployeeSignUpApprovalRequestDto dto, String email) {
        EmployeeListResponseDto responseDto = null;
        EmployeeSignUpApproval employeeSignUpApproval = null;
        Employee employee = null;

        employee = employeeRepository.findById(EmployeeId)
            .orElseThrow(IllegalArgumentException::new);

        employeeSignUpApproval = employeeSignUpApprovalRepository.findByEmployeeId(employee)
            .filter(a -> a.getAuthorizerId() == null)
            .orElse(null);

        if (employeeSignUpApproval == null) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessageKorean.NOT_MATCH_PASSWORD);
        }

        Employee authorizerEmployee = employeeRepository.findByLoginId(email)
            .orElseThrow(IllegalArgumentException::new);


        if (dto.getStatus().equals(IsApproved.APPROVED) && dto.getDeniedReason().isBlank()) {
            employee.setIsApproved(dto.getStatus());
            employeeSignUpApproval.setAuthorizerId(authorizerEmployee);
            employeeSignUpApproval.setStatus(dto.getStatus());
        } else if (dto.getStatus().equals(IsApproved.DENIED) && !dto.getDeniedReason().isBlank()) {
            employee.setIsApproved(dto.getStatus());
            employeeSignUpApproval.setAuthorizerId(authorizerEmployee);
            employeeSignUpApproval.setStatus(dto.getStatus());
            employeeSignUpApproval.setDeniedReason(dto.getDeniedReason());
        } else {
            throw new IllegalArgumentException();
        }

        employeeRepository.save(employee);
        employeeSignUpApprovalRepository.save(employeeSignUpApproval);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, null);
    }
}
