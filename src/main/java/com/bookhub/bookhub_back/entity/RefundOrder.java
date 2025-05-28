package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.RefundReason;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refund_orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RefundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_order_id")
    private Long refundOrderId;

    @Column(name = "refund_date_at", nullable = false)
    private LocalDateTime refundDateAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_reason", nullable = false)
    private RefundReason refundReason;

    // 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;
}
