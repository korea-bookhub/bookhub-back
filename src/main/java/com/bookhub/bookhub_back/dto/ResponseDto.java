package com.bookhub.bookhub_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private String code;     // 성공/실패 상태 코드
    private String message;  // 상태 메시지
    private T data;          // 응답 데이터

    // 성공 응답 (데이터 포함)
    public static <T> ResponseEntity<ResponseDto<T>> success(String code, String message, T data) {
        ResponseDto<T> response = new ResponseDto<>(code, message, data);
        return ResponseEntity.ok(response);
    }

    // 성공 응답 (데이터 없음)
    public static <T> ResponseEntity<ResponseDto<T>> success(String code, String message) {
        ResponseDto<T> response = new ResponseDto<>(code, message, null);
        return ResponseEntity.ok(response);
    }

    // 실패 응답
    public static ResponseEntity<ResponseDto<?>> fail(String code, String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ResponseDto<>(code, message, null));
    }
}
