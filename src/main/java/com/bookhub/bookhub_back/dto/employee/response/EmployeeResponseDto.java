package com.bookhub.bookhub_back.dto.employee.response;

import com.bookhub.bookhub_back.common.enums.IsApproved;
import com.bookhub.bookhub_back.common.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
    private Long employeeId;
    private Long employeeNumber;
    private String employeeName;
    private String branchName;
    private String positionName;
    private String authorityName;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private Status status;
    private IsApproved isApproved;
    private LocalDateTime createdAt;
}
