package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyCreateRequestDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyUpdateRequestDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyDetailResponseDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyListResponseDto;
import com.bookhub.bookhub_back.entity.DiscountPolicy;
import com.bookhub.bookhub_back.service.DiscountPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountPolicyServiceImpl implements DiscountPolicyService {
    @Override
    public ResponseDto<DiscountPolicyDetailResponseDto> createPolicy(DiscountPolicyCreateRequestDto dto) {
        DiscountPolicyDetailResponseDto responseDto = null;
        DiscountPolicy newPolicy = DiscountPolicy.builder().policyTitle(dto.getPolicyTitle()).policyDescription(dto.getPolicyDescription()).policyType(dto.getPolicyType()).discountPercent(dto.getDiscountPercent()).startDate(dto.getStartDate()).endDate(dto.getEndDate()).build();
        return null;
    }

    @Override
    public ResponseDto<DiscountPolicyDetailResponseDto> updatePolicy(Long policyId, DiscountPolicyUpdateRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<List<DiscountPolicyListResponseDto>> getAllPolicies() {
        return null;
    }

    @Override
    public ResponseDto<DiscountPolicyDetailResponseDto> getPolicyById(Long policyId) {
        return null;
    }

    @Override
    public ResponseDto<Void> deletePolicy(Long policyId) {
        return null;
    }
}
