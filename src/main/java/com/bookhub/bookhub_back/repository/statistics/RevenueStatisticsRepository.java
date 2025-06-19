package com.bookhub.bookhub_back.repository.statistics;

import com.bookhub.bookhub_back.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueStatisticsRepository extends JpaRepository<CustomerOrder,Long> {

    @Query(value =
            "SELECT " +
                    "  CASE DAYOFWEEK(co.customer_order_date_at) " +
                    "    WHEN 1 THEN '일' " +
                    "    WHEN 2 THEN '월' " +
                    "    WHEN 3 THEN '화' " +
                    "    WHEN 4 THEN '수' " +
                    "    WHEN 5 THEN '목' " +
                    "    WHEN 6 THEN '금' " +
                    "    WHEN 7 THEN '토' END AS weekday, " +
                    "  SUM(co.customer_order_total_price) AS total " +
                    "FROM customer_orders co " +
                    "WHERE YEAR(co.customer_order_date_at) = :year " +
                    "  AND MONTH(co.customer_order_date_at) BETWEEN :startMonth AND :endMonth " +
                    // ↓ GROUP BY를 CASE 전체 식으로 바꿔주면 ONLY_FULL_GROUP_BY와 충돌 안 남
                    "GROUP BY " +
                    "  CASE DAYOFWEEK(co.customer_order_date_at) " +
                    "    WHEN 1 THEN '일' " +
                    "    WHEN 2 THEN '월' " +
                    "    WHEN 3 THEN '화' " +
                    "    WHEN 4 THEN '수' " +
                    "    WHEN 5 THEN '목' " +
                    "    WHEN 6 THEN '금' " +
                    "    WHEN 7 THEN '토' END " +
                    "ORDER BY FIELD(weekday, '월','화','수','목','금','토','일')",
            nativeQuery = true)
    List<Object[]> findRevenueGroupedByWeekday(int year, int startMonth, int endMonth);
}
