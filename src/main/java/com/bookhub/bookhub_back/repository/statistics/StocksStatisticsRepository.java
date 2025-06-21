package com.bookhub.bookhub_back.repository.statistics;

import com.bookhub.bookhub_back.common.enums.StockActionType;
import com.bookhub.bookhub_back.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksStatisticsRepository extends JpaRepository<StockLog, Long> {
    @Query("""
            SELECT SUM(sl.amount)
            FROM StockLog sl
            WHERE sl.branchId.id = :branchId
            AND sl.stockActionType = :type
            AND FUNCTION('YEAR', sl.actionDate) = :year
            AND FUNCTION('MONTH', sl.actionDate) = :month
        """)
    Long sumAmountByBranchAndType(
        @Param("branchId") Long branchId,
        @Param("type") StockActionType type,
        @Param("year") int year,
        @Param("month") int month
    );
}
