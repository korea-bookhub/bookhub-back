package com.bookhub.bookhub_back.entity;

import com.bookhub.bookhub_back.entity.datetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Branch extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "branch_name", nullable = false, unique = true)
    private String branchName;

    @Column(name = "branch_location", nullable = false)
    private String branchLocation;

    // 참조관계
    @OneToMany(mappedBy = "branchId", fetch = FetchType.LAZY)
    // orphanRemoval = true
    private List<Employee> employees = new ArrayList<>();












}
