package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.common.enums.PolicyType;
import com.bookhub.bookhub_back.entity.DiscountPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountPolicyRepository extends JpaRepository< DiscountPolicy,Long> {
    //제목으로 할인정책 검색
    List<DiscountPolicy> findByPolicyTitleContainingIgnoreCase(String keyword);

    //PolicyType 로 할인정책 검색
    List<DiscountPolicy> searchDiscountPoliciesByPolicyType(PolicyType type);

    //기간 설정하여 할인정책 검색
    @Query("SELECT p FROM DiscountPolicy p WHERE p.startDate <= :end AND p.endDate >= :start")
    List<DiscountPolicy> getPoliciesByTime(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);

    Page<DiscountPolicy> findAll(Specification<DiscountPolicy> spec, Pageable pageable);
}
