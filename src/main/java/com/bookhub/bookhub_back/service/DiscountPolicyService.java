package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyCreateRequestDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyUpdateRequestDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyDetailResponseDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyListResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface DiscountPolicyService {
    ResponseDto<DiscountPolicyDetailResponseDto> createPolicy(@Valid DiscountPolicyCreateRequestDto dto);

    ResponseDto<DiscountPolicyDetailResponseDto> updatePolicy(Long policyId, @Valid DiscountPolicyUpdateRequestDto dto);

    ResponseDto<List<DiscountPolicyListResponseDto>> getAllPolicies();

    ResponseDto<DiscountPolicyDetailResponseDto> getPolicyById(Long policyId);

    ResponseDto<Void> deletePolicy(Long policyId);
}
