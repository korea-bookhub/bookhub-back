package com.bookhub.bookhub_back.dto.employee.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSignUpApprovalsReponseDto {
    private Long employeeId;
    private Long employeeName;
    private Long authorizerId;
    private Long authorizerName;
    private LocalDateTime appliedAt;
    private String status;
    private String deniedReason;
    private LocalDateTime updatedAt;
}
