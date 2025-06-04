package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.entity.Employee;
import com.bookhub.bookhub_back.repository.EmployeeRepository;
import com.bookhub.bookhub_back.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final Map<String, String> verificationTokens = new ConcurrentHashMap<>();
    private final EmployeeRepository employeeRepository;

    @Override
    public Mono<ResponseEntity<String>> sendEmailFindId(String email, String phoneNumber) {
        return Mono.fromSupplier(() -> {
            try {
                if (!employeeRepository.existsByEmail(email)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageKorean.NO_EXIST_USER_EMAIL);
                }
                if (!employeeRepository.existsByPhoneNumber(phoneNumber)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageKorean.NO_EXIST_USER_TEL);
                }

                String token = UUID.randomUUID().toString();
                verificationTokens.put(token, email);

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(email);
                helper.setSubject("이메일 인증 요청");
                String htmlContent = """
                        <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9;">
                            <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                                <h2 style="color: #333333;">[이메일 인증 요청]</h2>
                                <p style="font-size: 16px; color: #555555;">
                                    안녕하세요,<br><br>
                                    이메일 인증을 위해 아래 버튼을 클릭해 주세요.
                                </p>
                                <div style="text-align: center; margin: 30px 0;">
                                    <a href="http://localhost:8080/api/v1/auth/login-id-find?token=%s"
                                       style="display: inline-block; padding: 12px 24px; background-color: #4CAF50; color: #ffffff; 
                                              text-decoration: none; border-radius: 5px; font-size: 16px;">
                                        이메일 인증하기
                                    </a>
                                </div>
                                <p style="font-size: 12px; color: #cccccc; margin-top: 40px;">
                                    본 이메일은 인증 목적으로 발송되었습니다. 인증을 원하지 않으시면 무시하셔도 됩니다.
                                </p>
                            </div>
                        </div>
                    """.formatted(token);


                helper.setText(htmlContent, true);  // true: HTML 형식

                mailSender.send(message);

                return ResponseEntity.ok("인증 메일 전송 완료");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public Mono<ResponseEntity<String>> verifyEail(String token) {
        return Mono.fromSupplier(() -> {
            String email = verificationTokens.remove(token);
            Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(null);

            if (email != null && employee != null) {
                return ResponseEntity.ok(employee.getLoginId());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 또는 만료된 토큰입니다.");
            }
        });
    }
}
