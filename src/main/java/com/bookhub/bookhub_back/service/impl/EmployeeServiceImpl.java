package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.common.enums.ChangeType;
import com.bookhub.bookhub_back.common.enums.IsApproved;
import com.bookhub.bookhub_back.common.enums.Status;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeOrganizationUpdateRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpApprovalRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeDetailResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeListResponseDto;
import com.bookhub.bookhub_back.entity.Employee;
import com.bookhub.bookhub_back.entity.EmployeeChangeLog;
import com.bookhub.bookhub_back.entity.EmployeeSignUpApproval;
import com.bookhub.bookhub_back.repository.*;
import com.bookhub.bookhub_back.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeSignUpApprovalRepository employeeSignUpApprovalRepository;
    private final BranchRepository branchRepository;
    private final PositionRepository positionRepository;
    private final AuthorityRepository authorityRepository;
    private final EmployeeChangeLogRepository employeeChangeLogRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<EmployeeListResponseDto>> searchEmployee(
        String name,
        String branchName,
        String positionName,
        String authorityName,
        Status status
    ) {
        List<Employee> employees = null;
        List<EmployeeListResponseDto> responseDtos = null;

        employees = employeeRepository.searchEmployees(name, branchName, positionName, authorityName, status);


        responseDtos = employees.stream()
            .map(employee -> EmployeeListResponseDto.builder()
                .employeeId(employee.getEmployeeId())
                .employeeNumber(employee.getEmployeeNumber())
                .employeeName(employee.getName())
                .branchName(employee.getBranchId().getBranchName())
                .positionName(employee.getPositionId().getPositionName())
                .authorityName(employee.getAuthorityId().getAuthorityName())
                .status(employee.getStatus())
                .build())
            .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<EmployeeDetailResponseDto> getEmployeeById(Long employeeId) {
        EmployeeDetailResponseDto responseDto = null;
        Employee employee = null;

        employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다."));

        responseDto = EmployeeDetailResponseDto.builder()
            .employeeId(employee.getEmployeeId())
            .employeeNumber(employee.getEmployeeNumber())
            .employeeName(employee.getName())
            .branchName(employee.getBranchId().getBranchName())
            .positionName(employee.getPositionId().getPositionName())
            .authorityName(employee.getAuthorityId().getAuthorityName())
            .email(employee.getEmail())
            .phoneNumber(employee.getPhoneNumber())
            .birthDate(employee.getBirthDate())
            .status(employee.getIsApproved())
            .createdAt(employee.getCreatedAt())
            .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDto);
    }

    @Override
    @Transactional
    public ResponseDto<EmployeeListResponseDto> updateApproval(Long EmployeeId, EmployeeSignUpApprovalRequestDto dto, String loginId) {
        EmployeeListResponseDto responseDto = null;
        EmployeeSignUpApproval employeeSignUpApproval = null;
        Employee employee = null;

        employee = employeeRepository.findById(EmployeeId)
            .orElseThrow(IllegalArgumentException::new);

        employeeSignUpApproval = employeeSignUpApprovalRepository.findByEmployeeId(employee)
            .filter(a -> a.getAuthorizerId() == null)
            .orElse(null);

        if (employeeSignUpApproval == null) {
            throw new IllegalArgumentException();
        }

        Employee authorizerEmployee = employeeRepository.findByLoginId(loginId)
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

    @Override
    @Transactional
    public ResponseDto<Void> updateOrganization(Long employeeId, EmployeeOrganizationUpdateRequestDto dto, String loginId) {
        Employee employee = null;
        Employee authorizer = null;

        employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("직원 정보를 찾을 수 없습니다."));

        authorizer = employeeRepository.findByLoginId(loginId)
            .orElseThrow(() -> new IllegalArgumentException("직원 정보를 찾을 수 없습니다."));

        Long preBranchId = employee.getBranchId().getBranchId();
        Long prePositionId = employee.getPositionId().getPositionId();
        Long preAuthorityId = employee.getAuthorityId().getAuthorityId();

        if (dto.getBranchId() != null && !dto.getBranchId().equals(preBranchId)) {
            employee.setBranchId(branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("지점 정보가 정확하지 않습니다.")));

            EmployeeChangeLog employeeChangeLog = EmployeeChangeLog.builder()
                .employeeId(employee)
                .authorizerId(authorizer)
                .changeType(ChangeType.BRANCH_CHANGE)
                .previousBranchId(branchRepository.findById(preBranchId)
                    .orElseThrow(() -> new IllegalArgumentException("지점 정보가 정확하지 않습니다.")))
                .build();

            employeeChangeLogRepository.save(employeeChangeLog);
        }

        if (dto.getPositionId() != null && !dto.getPositionId().equals(prePositionId)) {
            employee.setPositionId(positionRepository.findById(dto.getPositionId())
                .orElseThrow(() -> new IllegalArgumentException("직급 정보가 정확하지 않습니다.")));

            EmployeeChangeLog employeeChangeLog = EmployeeChangeLog.builder()
                .employeeId(employee)
                .authorizerId(authorizer)
                .changeType(ChangeType.POSITION_CHANGE)
                .previousPositionId(positionRepository.findById(prePositionId)
                    .orElseThrow(() -> new IllegalArgumentException("직급 정보가 정확하지 않습니다.")))
                .build();

            employeeChangeLogRepository.save(employeeChangeLog);
        }

        if (dto.getAuthorityId() != null && !dto.getAuthorityId().equals(preAuthorityId)) {
            employee.setAuthorityId(authorityRepository.findById(dto.getAuthorityId())
                .orElseThrow(() -> new IllegalArgumentException("권한 정보가 정확하지 않습니다.")));

            EmployeeChangeLog employeeChangeLog = EmployeeChangeLog.builder()
                .employeeId(employee)
                .authorizerId(authorizer)
                .changeType(ChangeType.AUTHORITY_CHANGE)
                .previousAuthorityId(authorityRepository.findById(preAuthorityId)
                    .orElseThrow(() -> new IllegalArgumentException("권한 정보가 정확하지 않습니다.")))
                .build();

            employeeChangeLogRepository.save(employeeChangeLog);
        }

        employeeRepository.save(employee);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS);
    }
}
