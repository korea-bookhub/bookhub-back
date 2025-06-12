package com.bookhub.bookhub_back.dto.employee.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmployeeSignInResponseDto {
    private String token;
    private String loginId;
    private String email;
    private String name;
    private String BranchName;
    private String PositionName;
    private String AuthorityName;
    private int exprTime;
}