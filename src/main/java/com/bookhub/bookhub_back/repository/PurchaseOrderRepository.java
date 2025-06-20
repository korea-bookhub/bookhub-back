package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import com.bookhub.bookhub_back.entity.Book;
import com.bookhub.bookhub_back.entity.Branch;
import com.bookhub.bookhub_back.entity.Employee;
import com.bookhub.bookhub_back.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByEmployeeId(Employee employee);
    List<PurchaseOrder> findByBookIsbn(Book bookIsbn);
    List<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus);

    List<PurchaseOrder> findByEmployeeIdAndBookIsbn(Employee employee, Book book);
    List<PurchaseOrder> findByEmployeeIdAndPurchaseOrderStatus(Employee employee, PurchaseOrderStatus purchaseOrderStatus);
    List<PurchaseOrder> findByBookIsbnAndPurchaseOrderStatus(Book book, PurchaseOrderStatus purchaseOrderStatus);

    List<PurchaseOrder> findByEmployeeIdAndBookIsbnAndPurchaseOrderStatus(Employee employee, Book book, PurchaseOrderStatus purchaseOrderStatus);

    List<PurchaseOrder> findAllByBranchId(Branch branch);
}