package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.auth.request.LoginIdFindSendEmailRequestDto;
import com.bookhub.bookhub_back.dto.auth.request.PasswordFindSendEmailReqestDto;
import com.bookhub.bookhub_back.dto.auth.request.PasswordResetRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MailService {
    Mono<ResponseEntity<ResponseDto<String>>> sendEmailFindId(@Valid LoginIdFindSendEmailRequestDto dto);

    Mono<ResponseEntity<ResponseDto<String>>>  verifyEmailId(String token);

    Mono<ResponseEntity<String>> sendEmailResetPassword(@Valid PasswordFindSendEmailReqestDto dto);

    Mono<ResponseEntity<String>> verifyEmailPassword(String token, @Valid PasswordResetRequestDto dto);

}
