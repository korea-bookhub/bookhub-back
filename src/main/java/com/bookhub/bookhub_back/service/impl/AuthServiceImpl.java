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
import com.bookhub.bookhub_back.entity.*;
import com.bookhub.bookhub_back.provider.JwtProvider;
import com.bookhub.bookhub_back.repository.*;
import com.bookhub.bookhub_back.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final PositionRepository positionRepository;
    private final AuthorityRepository authorityRepository;
    private final EmployeeSignUpApprovalRepository employeeSignupApprovalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public ResponseDto<Void> signup(EmployeeSignUpRequestDto dto) {
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
            int randomSixDigits = 100000 + random.nextInt(900000); // 100000~999999 범위
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

        EmployeeSignUpApproval employeeSignupApproval = EmployeeSignUpApproval.builder()
            .employeeId(newEmployee)
            .appliedAt(newEmployee.getCreatedAt())
            .isApproved(newEmployee.getIsApproved())
            .build();

        employeeSignupApprovalRepository.save(employeeSignupApproval);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS);
    }

    @Override
    public ResponseDto<EmployeeSignInResponseDto> login(EmployeeSignInRequestDto dto) {
        String loginId = dto.getLoginId();
        String password = dto.getPassword();

        EmployeeSignInResponseDto responseDto = null;
        Employee employee = null;

        int exprTime = jwtProvider.getExpiration();

        employee = employeeRepository.findByLoginId(loginId)
            .orElseThrow(null);

        if (employee == null) {
            return ResponseDto.fail(ResponseCode.NO_EXIST_USER_ID, ResponseMessageKorean.NO_EXIST_USER_ID);
        }

        if (!bCryptPasswordEncoder.matches(password, employee.getPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessageKorean.NOT_MATCH_PASSWORD);
        }

        if (employee.getIsApproved().equals(IsApproved.PENDING) || employee.getIsApproved().equals(IsApproved.DENIED)) {
            return ResponseDto.fail(ResponseCode.NO_PERMISSION, ResponseMessageKorean.NO_PERMISSION);
        }

        String token = jwtProvider.generateJwtToken(loginId, employee.getAuthorityId());

        responseDto = EmployeeSignInResponseDto.builder()
            .token(token)
            .exprTime(exprTime)
            .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<Void> checkLoginIdDuplicate(String loginId) {
        if (employeeRepository.existsByLoginId(loginId)) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessageKorean.DUPLICATED_USER_ID);
        }
        return ResponseDto.success(ResponseCode.SUCCESS, "사용 가능한 아이디입니다.");
    }

    @Override
    public ResponseDto<Void> logout(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
            .path("/")
            .maxAge(0)
            .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS);
    }

}