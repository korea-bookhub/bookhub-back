package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.common.enums.PolicyType;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyCreateRequestDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyUpdateRequestDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyDetailResponseDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyListResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface DiscountPolicyService {
    ResponseDto<Void> createPolicy(@Valid DiscountPolicyCreateRequestDto dto);

    ResponseDto<Void> updatePolicy(Long policyId, @Valid DiscountPolicyUpdateRequestDto dto);

    ResponseDto<List<DiscountPolicyListResponseDto>> getAllPolicies();

    ResponseDto<DiscountPolicyDetailResponseDto> getPolicyById(Long policyId);

    ResponseDto<List<DiscountPolicyListResponseDto>> searchPoliciesByTitle(String keyword);

    ResponseDto<List<DiscountPolicyListResponseDto>> getPoliciesByType(PolicyType type);

    ResponseDto<Void> deletePolicy(Long policyId);
}
