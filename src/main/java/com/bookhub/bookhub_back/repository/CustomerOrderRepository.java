package com.bookhub.bookhub_back.repository;

import com.bookhub.bookhub_back.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository< CustomerOrder,Long> {
}
