package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.EmployeeSignupApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSignupApprovalRepository extends JpaRepository<EmployeeSignupApproval, Long> {
}
