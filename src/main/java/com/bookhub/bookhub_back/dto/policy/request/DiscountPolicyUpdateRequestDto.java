package com.bookhub.bookhub_back.dto.policy.request;

import com.bookhub.bookhub_back.common.enums.PolicyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountPolicyUpdateRequestDto {
    private String policyDescription;
    private PolicyType policyType;
    private int totalPriceAchieve;
    private int discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}
