package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.common.enums.PolicyType;
import com.bookhub.bookhub_back.entity.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountPolicyRepository extends JpaRepository< DiscountPolicy,Long> {
    //제목으로 할인정책 검색
    List<DiscountPolicy> findByPolicyTitleContainingIgnoreCase(String keyword);

    //PolicyType 로 할인정책 검색
    List<DiscountPolicy> searchDiscountPoliciesByPolicyType(PolicyType type);
}
