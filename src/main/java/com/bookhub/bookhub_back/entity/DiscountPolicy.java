package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.PolicyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//135
//완료
@Entity
@Table(name = "discount_policies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    @Column(name = "policy_title", nullable = false)
    private String policyTitle;

    @Lob
    @Column(name = "policy_description")
    private String policyDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type", nullable = false) //할인방법 (책, 카테고리, 총 금액)
    private PolicyType policyType;

    @Column(name = "total_price_achieve") //총 금액 할인시 기준가격
    private int totalPriceAchieve;

    @Column(name = "discount_percent", nullable = false)
    private int discountPercent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "policyId")
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "policyId")
    private List<BookCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "appliedPolicyId")
    private List<CustomerOrder> customerOrders = new ArrayList<>();



}
