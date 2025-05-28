package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_id")
    private Long purchaseOrderId;

    @Column(name = "purchase_order_amount", nullable = false)
    private int purchaseOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_order_status", nullable = false)
    private PurchaseOrderStatus purchaseOrderStatus;

    @Column(name = "purchase_order_date_at", nullable = false)
    private LocalDateTime purchaseOrderDateAt = LocalDateTime.now();

    // 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY)
    private List<PurchaseOrderApproval> purchaseOrderApprovals = new ArrayList<>();
}
