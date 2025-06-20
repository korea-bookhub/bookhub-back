package com.bookhub.bookhub_back.repository.statistics;

import com.bookhub.bookhub_back.dto.statistics.response.revenue.BranchRevenueResponseDto;
import com.bookhub.bookhub_back.dto.statistics.response.revenue.MonthlyRevenueResponseDto;
import com.bookhub.bookhub_back.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RevenueStatisticsRepository extends JpaRepository<CustomerOrder,Long> {
//
//    @Query(value =
//            "SELECT " +
//                    "  CASE DAYOFWEEK(co.customer_order_date_at) " +
//                    "    WHEN 1 THEN '일' WHEN 2 THEN '월' WHEN 3 THEN '화' WHEN 4 THEN '수' " +
//                    "    WHEN 5 THEN '목' WHEN 6 THEN '금' WHEN 7 THEN '토' END AS weekday, " +
//                    "  COALESCE(SUM(co.customer_order_total_price), 0) AS total " +
//                    "FROM customer_orders co " +
//                    "WHERE YEAR(co.customer_order_date_at) = :year " +
//                    "  AND MONTH(co.customer_order_date_at) BETWEEN :startMonth AND :endMonth " +
//                    "GROUP BY " +
//                    "  CASE DAYOFWEEK(co.customer_order_date_at) " +
//                    "    WHEN 1 THEN '일' WHEN 2 THEN '월' WHEN 3 THEN '화' WHEN 4 THEN '수' " +
//                    "    WHEN 5 THEN '목' WHEN 6 THEN '금' WHEN 7 THEN '토' END " +
//                    "ORDER BY FIELD(weekday, '월','화','수','목','금','토','일')",
//            nativeQuery = true)
//    List<Object[]> findRevenueGroupedByWeekday(int year, int startMonth, int endMonth);
//
//    @Query(
//            "SELECT new com.bookhub.bookhub_back.dto.statistics.response.revenue.MonthlyRevenueResponseDto( " +
//                    "  FUNCTION('MONTH', o.createdAt), " +
//                    "  COALESCE(SUM(o.totalPrice), 0L) " +
//                    ") " +
//                    "FROM CustomerOrder o " +
//                    "WHERE o.createdAt BETWEEN :startDate AND :endDate " +
//                    "GROUP BY FUNCTION('MONTH', o.createdAt) " +
//                    "ORDER BY FUNCTION('MONTH', o.createdAt)"
//    )
//    List<MonthlyRevenueResponseDto> findMonthlySales(LocalDate startDate, LocalDate endDate);
//
//    @Query("""
//        SELECT new com.bookhub.bookhub_back.dto.statistics.response.revenue.BranchRevenueResponseDto(
//          b.branchId,
//          b.branchName,
//          cat.categoryName,
//          SUM(d.price * d.amount)
//        )
//        FROM CustomerOrder o
//        JOIN o.branchId b
//        JOIN o.customerOrderDetails d
//        JOIN d.book bk
//        JOIN bk.categoryId cat
//        WHERE FUNCTION('DATE', o.createdAt)
//              BETWEEN :startDate AND :endDate
//        GROUP BY b.branchId, b.branchName, cat.categoryName
//        ORDER BY b.branchId
//        """)
//    List<BranchRevenueResponseDto> findByBranchByDate(@Param("startDate") LocalDate startDate,
//                                                      @Param("endDate")   LocalDate endDate);
//
//    List<CustomerOrder> findByCreatedAtBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
