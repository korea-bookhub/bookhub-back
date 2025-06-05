package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.StockActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks_logs")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StockLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long StockLogId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private StockActionType stockActionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn")
    private Book bookIsbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_branch_id", nullable = false)
    private Branch targetBranchId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "book_amount", nullable = false)
    private Long bookAmount;

    @Column(name = "action_date", nullable = false)
    private LocalDateTime actionDate;

    @Lob
    @Column(name = "description")
    private String description;
}