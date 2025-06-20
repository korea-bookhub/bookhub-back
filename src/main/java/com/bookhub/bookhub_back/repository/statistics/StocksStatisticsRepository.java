package com.bookhub.bookhub_back.repository.statistics;

import com.bookhub.bookhub_back.entity.Stock;
import com.bookhub.bookhub_back.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksStatisticsRepository extends JpaRepository<StockLog, Long> {
}
