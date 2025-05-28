package com.bookhub.bookhub_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Book;
import java.time.LocalDateTime;

//316
//dateat 결제할때 시간 생성하는거
//완료
@Entity
@Table(name = "customer_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_order_id")
    private Long customerOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @Column(name = "customer_id")
    private Customer customer;

    @Column(name = "customer_order_total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "customer_order_total_price", nullable = false)
    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applied_policy_id")
    private DiscountPolicy appliedPolicyId;

    @Column(name = "customer_order_date_at")
    private LocalDateTime customerOrderDateAt;







}
