package com.bookhub.bookhub_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_reception_approvals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookReceptionApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_reception_approval_id")
    private Long bookReceptionApprovalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branchId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_approval_id", nullable = false)
    private PurchaseOrderApproval purchaseOrderApproval;

    @Column(name = "is_reception_approved", nullable = false)
    private Boolean isReceptionApproved = false;

    @Column(name = "reception_date_at", nullable = false)
    private LocalDateTime receptionDateAt;
}
