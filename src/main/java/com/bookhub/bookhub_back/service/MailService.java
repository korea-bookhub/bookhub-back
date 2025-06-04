package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.common.constants.RegexConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MailService {
    Mono<ResponseEntity<String>> sendEmailFindId(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식으로 입력해야 합니다.")
        String email,
        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = RegexConstants.PHONE_REGEX, message = "휴대폰 번호는 010으로 시작하고 뒤에는 8자리로 이루어져야 합니다.")
        String phoneNumber);

    Mono<ResponseEntity<String>> verifyEail(String token);
}
