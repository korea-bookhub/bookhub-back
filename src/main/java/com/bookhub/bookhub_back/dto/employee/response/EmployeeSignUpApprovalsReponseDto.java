package com.bookhub.bookhub_back.dto.employee.response;

import com.bookhub.bookhub_back.common.enums.IsApproved;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeSignUpApprovalsReponseDto {
    private Long approvalId;
    private Long employeeId;
    private Long employeeNumber;
    private String employeeName;
    private String branchName;
    private String email;
    private String phoneNumber;
    private Long authorizerId;
    private String authorizerName;
    private LocalDateTime appliedAt;
    private IsApproved isApproved;
    private String deniedReason;
    private LocalDateTime updatedAt;
}
