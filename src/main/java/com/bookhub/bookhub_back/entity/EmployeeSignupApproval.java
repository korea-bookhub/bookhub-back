package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_signup_approvals")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class EmployeeSignupApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approval_id")
    private Long approvalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "authorizer_id", nullable = false)
    private Employee authorizerId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @Column(name = "approved_denied_at", nullable = false)
    @CreatedDate
    private LocalDateTime approvedDeniedAt;

    @Column(name = "denied_reason")
    private String deniedReason;
}
