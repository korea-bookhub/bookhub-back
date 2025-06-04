package com.bookhub.bookhub_back.dto.policy.response;

import com.bookhub.bookhub_back.common.enums.PolicyType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class DiscountPolicyListResponseDto {
    private Long policyId;
    private String policyTitle;
    private PolicyType policyType;
    private LocalDate startDate;
    private LocalDate endDate;
}
