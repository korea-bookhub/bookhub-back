package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.auth.request.LoginIdFindSendEmailRequestDto;
import com.bookhub.bookhub_back.dto.auth.request.PasswordFindSendEmailReqestDto;
import com.bookhub.bookhub_back.dto.auth.request.PasswordResetRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MailService {
    Mono<ResponseEntity<String>> sendEmailFindId(@Valid LoginIdFindSendEmailRequestDto dto);

    Mono<ResponseEntity<String>> verifyEailId(String token);

    Mono<ResponseEntity<String>> sendEmailResetPassword(@Valid PasswordFindSendEmailReqestDto dto);

    Mono<ResponseEntity<String>> verifyEailPassword(String token, @Valid PasswordResetRequestDto dto);
}
