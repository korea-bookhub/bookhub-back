package com.bookhub.bookhub_back.dto.policy.response;

import com.bookhub.bookhub_back.common.enums.PolicyType;

import java.time.LocalDate;

public class DiscountPolicyListResponseDto {
    private Long publisherid;
    private String policyTitle;
    private PolicyType policyType;
    private LocalDate startDate;
    private LocalDate endDate;
}
