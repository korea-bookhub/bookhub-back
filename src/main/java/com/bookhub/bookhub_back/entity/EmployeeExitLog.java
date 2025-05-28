package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.common.enums.ExitReason;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_exit_logs")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmployeeExitLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exit_id")
    private Long exitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "exit_at", nullable = false)
    private LocalDateTime exitAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "exit_reason", nullable = false)
    private ExitReason exitReason;
}
