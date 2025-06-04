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
import com.bookhub.bookhub_back.repository.BranchRepository;
import com.bookhub.bookhub_back.repository.EmployeeRepository;
import com.bookhub.bookhub_back.repository.PositionRepository;
import com.bookhub.bookhub_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final PositionRepository positionRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto<EmployeeSignUpResponseDto> signup(EmployeeSignUpRequestDto dto) {
        EmployeeSignUpResponseDto responseDto = null;
        Employee newEmployee = null;

        String loginId = dto.getLoginId();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        String email = dto.getEmail();
        String phoneNumber = dto.getPhoneNumber();

        if (employeeRepository.existsByLoginId(loginId)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessageKorean.DUPLICATED_USER_ID);
        }

        if (!password.equals(confirmPassword)) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessageKorean.NOT_MATCH_PASSWORD);
        }

        if (employeeRepository.existsByEmail(email)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_EMAIL, ResponseMessageKorean.DUPLICATED_EMAIL);
        }

        if (employeeRepository.existsByPhoneNumber(phoneNumber)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessageKorean.DUPLICATED_TEL_NUMBER);
        }

        String encodePassword = bCryptPasswordEncoder.encode(password);

        Position position = positionRepository.findByPositionName("사원")
            .orElseGet(() -> positionRepository.save(Position.builder()
                .positionName("사원")
                .build()));

        Authority authority = authorityRepository.findByAuthorityName("STAFF")
            .orElseGet(() -> authorityRepository.save(Authority.builder()
                .authorityName("STAFF")
                .build()));

        Branch branch = branchRepository.findById(dto.getBranchId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 지점입니다."));

        Random random = new Random();
        Long employeeNumber;

        while (true) {
            int randomSixDigits = 000000 + random.nextInt(900000); // 100000~999999 범위
            String resultStr = String.format("%02d", LocalDate.now().getYear() % 100) + randomSixDigits;
            employeeNumber = Long.parseLong(resultStr);

            if (!employeeRepository.existsByEmployeeNumber(employeeNumber)) {
                break;
            }
        }

        newEmployee = Employee.builder()
            .loginId(dto.getLoginId())
            .password(encodePassword)
            .email(email)
            .employeeNumber(employeeNumber)
            .name(dto.getName())
            .branchId(branch)
            .positionId(position)
            .authorityId(authority)
            .phoneNumber(phoneNumber)
            .birthDate(dto.getBirthDate())
            .isApproved(IsApproved.PENDING)
            .status(Status.EMPLOYED)
            .build();

        employeeRepository.save(newEmployee);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS);
    }

    @Override
    public ResponseDto<EmployeeSignInResponseDto> login(EmployeeSignInRequestDto dto) {
        return null;
    }
}