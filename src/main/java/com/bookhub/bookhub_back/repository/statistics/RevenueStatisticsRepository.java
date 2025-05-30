package com.bookhub.bookhub_back.repository.statistics;

import com.bookhub.bookhub_back.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueStatisticsRepository extends JpaRepository<CustomerOrder,Long> {
}
