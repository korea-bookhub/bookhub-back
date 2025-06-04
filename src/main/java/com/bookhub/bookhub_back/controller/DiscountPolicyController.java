package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.enums.PolicyType;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyCreateRequestDto;
import com.bookhub.bookhub_back.dto.policy.request.DiscountPolicyUpdateRequestDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyDetailResponseDto;
import com.bookhub.bookhub_back.dto.policy.response.DiscountPolicyListResponseDto;
import com.bookhub.bookhub_back.service.DiscountPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class DiscountPolicyController {
    //서비스 단이랑 연결
    private final DiscountPolicyService discountPolicyService;

    //1) 할인 정책 생성
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createPolicy(
            @Valid @RequestBody DiscountPolicyCreateRequestDto dto){
        ResponseDto<Void> discountPolicy = discountPolicyService.createPolicy(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountPolicy);
    }

    //2) 할인 정책 수정
    @PutMapping("/{policyId}")
    public ResponseEntity<ResponseDto<Void>> updatePolicy(
            @PathVariable Long policyId,
            @Valid @RequestBody DiscountPolicyUpdateRequestDto dto){
        ResponseDto<Void> responseDto = discountPolicyService.updatePolicy(policyId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    //3) 할인 정책 전체조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<DiscountPolicyListResponseDto>>> getAllPolicies(){
        ResponseDto<List<DiscountPolicyListResponseDto>> policies = discountPolicyService.getAllPolicies();
        return ResponseEntity.status(HttpStatus.OK).body(policies);
    }

    //4) 할인 정책 단건 조회
    @GetMapping("/{policyId}")
    public ResponseEntity<ResponseDto<DiscountPolicyDetailResponseDto>> getPolicyById(
            @PathVariable Long policyId){
        ResponseDto<DiscountPolicyDetailResponseDto> policy = discountPolicyService.getPolicyById(policyId);
        return ResponseEntity.status(HttpStatus.OK).body(policy);
    }

    //5) 할인 정책 제목으로 검색
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<DiscountPolicyListResponseDto>>> searchPoliciesByTitle(
            @RequestParam String keyword){
        ResponseDto<List<DiscountPolicyListResponseDto>> responseDto = discountPolicyService.searchPoliciesByTitle(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //6) 할인 정책 PolicyType로 검색
    @GetMapping("/{policyType}")
    public ResponseEntity<ResponseDto<List<DiscountPolicyListResponseDto>>> getPoliciesByType(
            @RequestParam PolicyType type){
        ResponseDto<List<DiscountPolicyListResponseDto>> responseDto = discountPolicyService.getPoliciesByType(type);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    //7) 할인 정책 삭제
    @DeleteMapping("/{policyId}")
    public ResponseEntity<ResponseDto<Void>> deletePolicy(@PathVariable Long policyId){
        ResponseDto<Void> responseDto = discountPolicyService.deletePolicy(policyId);
        return ResponseEntity.noContent().build();
    }

}
