package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.employee.request.SignInRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.SignUpRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.SignInResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.SignUpResponseDto;
import com.bookhub.bookhub_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 1) 회원가입
    @PostMapping(POST_SIGN_UP)
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signup(@Valid @RequestBody SignUpRequestDto dto) {
        System.out.println("=== 회원가입 요청 도착 ===");
        ResponseDto<SignUpResponseDto> response = authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<SignInResponseDto>> login(@Valid @RequestBody SignInRequestDto dto) {
        ResponseDto<SignInResponseDto> response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}