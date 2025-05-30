package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.common.enums.IsApproved;
import com.bookhub.bookhub_back.common.enums.Status;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignInRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignInResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignUpResponseDto;
import com.bookhub.bookhub_back.entity.Authority;
import com.bookhub.bookhub_back.entity.Branch;
import com.bookhub.bookhub_back.entity.Employee;
import com.bookhub.bookhub_back.entity.Position;
import com.bookhub.bookhub_back.provider.JwtProvider;
import com.bookhub.bookhub_back.repository.AuthorityRepository;
import com.bookhub.bookhub_back.repository.EmployeeRepository;
import com.bookhub.bookhub_back.repository.PositionRepository;
import com.bookhub.bookhub_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto<EmployeeSignUpResponseDto> signup(EmployeeSignUpRequestDto dto) {
        EmployeeSignUpResponseDto responseDto = null;
        Employee employee = null;

        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        String email = dto.getEmail();
        String phoneNumber = dto.getPhoneNumber();

        if (!password.equals(confirmPassword)) {
            return new ResponseDto<>(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessageKorean.NOT_MATCH_PASSWORD, null);
        }

        if (employeeRepository.existsByEmail(email)) {
            return new ResponseDto<>(ResponseCode.DUPLICATED_EMAIL, ResponseMessageKorean.DUPLICATED_EMAIL, null);
        }

        if (employeeRepository.existsByPhoneNumber(phoneNumber)) {
            return new ResponseDto<>(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessageKorean.DUPLICATED_TEL_NUMBER, null);
        }

        String encodePassword = bCryptPasswordEncoder.encode(password);

        Position position = positionRepository.findByPositionName("사원")
            .orElseGet(() -> positionRepository.save(Position.builder()
                .positionName("사원")
                .build()));

        Authority authority = authorityRepository.findByAuthorityName("STAFF")
            .orElseGet(() -> authorityRepository.save(Authority.builder()
                .authorityName("사원")
                .build()));
        Long a = 2000001L;
        employee  = Employee.builder()
            .loginId(dto.getLoginId())
            .password(encodePassword)
            .email(email)
            .employeeNumber(a)
            .name(dto.getName())
            .branchId(Branch.builder().branchId(dto.getBranchId()).build())
            .positionId(position)
            .authorityId(authority)
            .phoneNumber(phoneNumber)
            .birthDate(dto.getBirthDate())
            .isApproved(IsApproved.PENDING)
            .status(Status.EMPLOYED)
            .build();

        employeeRepository.save(employee);

        responseDto = new EmployeeSignUpResponseDto(employee);

        return new ResponseDto<>(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<EmployeeSignInResponseDto> login(EmployeeSignInRequestDto dto) {
        return null;
    }
}