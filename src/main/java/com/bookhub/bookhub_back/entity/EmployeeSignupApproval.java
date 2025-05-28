package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_signup_approvals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmployeeSignupApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long approvalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "status", nullable = false)
    private Employee authorizer;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @Column(name = "approved_denied_at", nullable = false)
    private LocalDateTime approvedDeniedAt = LocalDateTime.now();

    @Column(name = "denied_reason")
    private String deniedReason;
}
