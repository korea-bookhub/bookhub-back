package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository< DiscountPolicy,Long> {
}
