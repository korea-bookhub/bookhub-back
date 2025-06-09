package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.Employee;
import com.bookhub.bookhub_back.entity.PurchaseOrder;
import com.bookhub.bookhub_back.entity.PurchaseOrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderApprovalRepository extends JpaRepository<PurchaseOrderApproval, Long> {
    List<PurchaseOrderApproval> findByEmployeeId(Employee employee);

    @Query(value = "SELECT * FROM purchase_order_approvals WHERE approved_date_at BETWEEN MONTH(:startedDate) AND MONTH(:endedDate)", nativeQuery = true)
    List<PurchaseOrderApproval> findByCreatedAt(@Param("startedDate") LocalDate startedDate, @Param("endedDate") LocalDate endedDate);

    List<PurchaseOrderApproval> findByIsApproved(boolean isApproved);
}
