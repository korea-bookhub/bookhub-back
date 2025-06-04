package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.common.enums.PolicyType;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyCreateRequestDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyUpdateRequestDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyDetailResponseDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyListResponseDto;
import com.bookhub.bookhub_back.entity.DiscountPolicy;
import com.bookhub.bookhub_back.repository.DiscountPolicyRepository;
import com.bookhub.bookhub_back.service.DiscountPolicyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountPolicyServiceImpl implements DiscountPolicyService {

    //repository와 연결
    private final DiscountPolicyRepository policyRepository;

    //1) 할인 정책 생성
    @Override
    public ResponseDto<Void> createPolicy(DiscountPolicyCreateRequestDto dto) {

        if (dto.getPolicyType() == PolicyType.TOTAL_PRICE_DISCOUNT && dto.getTotalPriceAchieve() == null) {
            throw new IllegalArgumentException(ResponseCode.VALIDATION_FAIL);
        }

        if (dto.getPolicyType() != PolicyType.TOTAL_PRICE_DISCOUNT && dto.getTotalPriceAchieve() != null) {
            throw new IllegalArgumentException(ResponseCode.VALIDATION_FAIL);
        }

        DiscountPolicy newPolicy = DiscountPolicy.builder()
                .policyTitle(dto.getPolicyTitle())
                .policyDescription(dto.getPolicyDescription())
                .policyType(dto.getPolicyType())
                .totalPriceAchieve(dto.getTotalPriceAchieve())
                .discountPercent(dto.getDiscountPercent())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        DiscountPolicy saved = policyRepository.save(newPolicy);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    //2) 할인 정책 수정
    @Override
    public ResponseDto<Void> updatePolicy(Long policyId, DiscountPolicyUpdateRequestDto dto) {
        DiscountPolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.NO_EXIST_ID +policyId));

        if (dto.getPolicyDescription() != null) policy.setPolicyDescription(dto.getPolicyDescription());
        if (dto.getTotalPriceAchieve() != null){
            if(policy.getPolicyType() == PolicyType.TOTAL_PRICE_DISCOUNT){
                policy.setTotalPriceAchieve(dto.getTotalPriceAchieve());
            }else{
                throw new IllegalArgumentException(ResponseCode.VALIDATION_FAIL);}}
        if (dto.getDiscountPercent() != null) policy.setDiscountPercent(dto.getDiscountPercent());
        if (dto.getStartDate() != null) policy.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) policy.setEndDate(dto.getEndDate());

        DiscountPolicy updatedPolicy = policyRepository.save(policy);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

    }

    //3) 할인 정책 전체조회
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<DiscountPolicyListResponseDto>> getAllPolicies() {
        List<DiscountPolicyListResponseDto> responseDtos = null;

        List<DiscountPolicy> policies = policyRepository.findAll();

        responseDtos = policies.stream()
                .map(policy -> DiscountPolicyListResponseDto.builder()
                        .policyId(policy.getPolicyId())
                        .policyTitle(policy.getPolicyTitle())
                        .policyType(policy.getPolicyType())
                        .startDate(policy.getStartDate())
                        .endDate(policy.getEndDate())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    //4) 할인 정책 단건 조회
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<DiscountPolicyDetailResponseDto> getPolicyById(Long policyId) {
        DiscountPolicyDetailResponseDto responseDto = null;

        DiscountPolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.NO_EXIST_ID + policyId));

        responseDto = DiscountPolicyDetailResponseDto.builder()
                .policyTitle(policy.getPolicyTitle())
                .policyDescription(policy.getPolicyDescription())
                .policyType(policy.getPolicyType())
                .totalPriceAchieve(policy.getTotalPriceAchieve())
                .discountPercent(policy.getDiscountPercent())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    //5) 할인 정책 제목으로 검색
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<DiscountPolicyListResponseDto>> searchPoliciesByTitle(String keyword) {
        List<DiscountPolicyListResponseDto> responseDtos = null;

        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseDto.fail(ResponseCode.REQUIRED_FIELD_MISSING,ResponseMessage.REQUIRED_FIELD_MISSING);
        }

        List<DiscountPolicy> policies = policyRepository.findByPolicyTitleContainingIgnoreCase(keyword);

        responseDtos = policies.stream()
                .map(policy -> DiscountPolicyListResponseDto.builder()
                        .policyId(policy.getPolicyId())
                        .policyTitle(policy.getPolicyTitle())
                        .policyType(policy.getPolicyType())
                        .startDate(policy.getStartDate())
                        .endDate(policy.getEndDate())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    //6) 할인 정책 PolicyType로 검색
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<DiscountPolicyListResponseDto>> getPoliciesByType(PolicyType type) {
        List<DiscountPolicyListResponseDto> responseDtos = null;

        List<DiscountPolicy> policies = policyRepository.searchDiscountPoliciesByPolicyType (type);

        responseDtos = policies.stream()
                .map(policy -> DiscountPolicyListResponseDto.builder()
                        .policyId(policy.getPolicyId())
                        .policyTitle(policy.getPolicyTitle())
                        .policyType(policy.getPolicyType())
                        .startDate(policy.getStartDate())
                        .endDate(policy.getEndDate())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    //7) 할인 정책 삭제
    @Override
    public ResponseDto<Void> deletePolicy(Long policyId) {
        DiscountPolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.NO_EXIST_ID + policyId));

        policyRepository.deleteById(policyId);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
}
