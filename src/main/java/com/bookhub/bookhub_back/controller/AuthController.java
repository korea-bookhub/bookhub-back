package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.auth.request.LoginIdFindSendEmailRequestDto;
import com.bookhub.bookhub_back.dto.auth.request.PasswordFindSendEmailReqestDto;
import com.bookhub.bookhub_back.dto.auth.request.PasswordResetRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignInRequestDto;
import com.bookhub.bookhub_back.dto.employee.request.EmployeeSignUpRequestDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignInResponseDto;
import com.bookhub.bookhub_back.dto.employee.response.EmployeeSignUpResponseDto;
import com.bookhub.bookhub_back.service.AuthService;
import com.bookhub.bookhub_back.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 1) 회원가입
    @PostMapping(POST_SIGN_UP)
    public ResponseEntity<ResponseDto<EmployeeSignUpResponseDto>> signup(@Valid @RequestBody EmployeeSignUpRequestDto dto) {
        ResponseDto<EmployeeSignUpResponseDto> response = authService.signup(dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }


    // 2) 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<EmployeeSignInResponseDto>> login(@Valid @RequestBody EmployeeSignInRequestDto dto) {
        ResponseDto<EmployeeSignInResponseDto> response = authService.login(dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    @PostMapping("/login-id-find/email")
    public Mono<ResponseEntity<String>> SendEmailFindId(@Valid @RequestBody LoginIdFindSendEmailRequestDto dto) {
        return mailService.sendEmailFindId(dto);
    }

    @GetMapping("/login-id-find")
    public Mono<ResponseEntity<String>> verifyEmailId(@RequestParam String token) {
        return mailService.verifyEailId(token);
    }

    @PostMapping("/password-change/email")
    public Mono<ResponseEntity<String>> sendEmailResetPassword (@Valid @RequestBody PasswordFindSendEmailReqestDto dto) {
        return mailService.sendEmailResetPassword(dto);
    }

    @PutMapping("/password-change")
    public Mono<ResponseEntity<String>> verifyEmailPassword(@RequestParam String token, @Valid @RequestBody PasswordResetRequestDto dto) {
        return mailService.verifyEailPassword(token, dto);
    }
}