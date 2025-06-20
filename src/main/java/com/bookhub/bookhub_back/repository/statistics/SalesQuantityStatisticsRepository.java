package com.bookhub.bookhub_back.repository.statistics;

import com.bookhub.bookhub_back.dto.statistics.projection.BestSellerProjection;
import com.bookhub.bookhub_back.dto.statistics.projection.SalesQuantityStatisticsProjection;
import com.bookhub.bookhub_back.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesQuantityStatisticsRepository extends JpaRepository<CustomerOrder, Long> {
    // 총합 베스트 셀러 (100위까지)
    @Query(value = """
            SELECT b.book_isbn AS isbn,
                   b.book_title AS bookTitle,
                   a.author_name AS authorName,
                   p.publisher_name AS publisherName,
                   bc.category_name AS categoryName,
                   b.cover_url AS coverUrl,
                   SUM(cod.amount) AS totalSales
                         FROM customer_orders_detail cod
                         JOIN books b ON cod.book_isbn = b.book_isbn
                         JOIN authors a ON b.author_id = a.author_id
                         JOIN publishers p ON b.publisher_id = p.publisher_id
                         JOIN book_categories bc ON b.category_id = bc.category_id
                         GROUP BY b.book_isbn
                         ORDER BY totalSales DESC
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      LIMIT 100;
        """, nativeQuery = true)
    List<BestSellerProjection> findTop100BestSellers();

    // 기간별 베스트셀러 (100위까지)
    @Query(value = """
            SELECT b.book_isbn AS isbn, 
                   b.book_title AS bookTitle,
                   a.author_name AS authorName, 
                   p.publisher_name AS publisherName, 
                   bc.category_name AS categoryName, 
                   b.cover_url AS coverUrl,
                   SUM(cod.amount) AS totalSales
                         FROM customer_orders_detail cod
                         JOIN customer_orders co ON cod.customer_order_id = co.customer_order_id
                         JOIN books b ON cod.book_isbn = b.book_isbn
                         JOIN authors a ON b.author_id = a.author_id
                         JOIN publishers p ON b.publisher_id = p.publisher_id
                         JOIN book_categories bc ON b.category_id = bc.category_id
                         WHERE co.customer_order_date_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
                         GROUP BY b.book_isbn
                         ORDER BY totalSales DESC
                         LIMIT 100;
        """, nativeQuery = true)
    List<BestSellerProjection> findWeeklyBestSellers();

    @Query(value = """
            SELECT b.book_isbn AS isbn, 
                   b.book_title AS bookTitle,
                   a.author_name AS authorName, 
                   p.publisher_name AS publisherName, 
                   bc.category_name AS categoryName, 
                   b.cover_url AS coverUrl,
                   SUM(cod.amount) AS totalSales
                         FROM customer_orders_detail cod
                         JOIN customer_orders co ON cod.customer_order_id = co.customer_order_id
                         JOIN books b ON cod.book_isbn = b.book_isbn
                         JOIN authors a ON b.author_id = a.author_id
                         JOIN publishers p ON b.publisher_id = p.publisher_id
                         JOIN book_categories bc ON b.category_id = bc.category_id
                         WHERE co.customer_order_date_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH)
                         GROUP BY b.book_isbn
                         ORDER BY totalSales DESC
                         LIMIT 100;
        """, nativeQuery = true)
    List<BestSellerProjection> findMonthlyBestSellers();

    @Query(value = """
            SELECT b.book_isbn AS isbn, 
                   b.book_title AS bookTitle,
                   a.author_name AS authorName, 
                   p.publisher_name AS publisherName, 
                   bc.category_name AS categoryName, 
                   b.cover_url AS coverUrl,
                   SUM(cod.amount) AS totalSales
                         FROM customer_orders_detail cod
                         JOIN customer_orders co ON cod.customer_order_id = co.customer_order_id
                         JOIN books b ON cod.book_isbn = b.book_isbn
                         JOIN authors a ON b.author_id = a.author_id
                         JOIN publishers p ON b.publisher_id = p.publisher_id
                         JOIN book_categories bc ON b.category_id = bc.category_id
                         WHERE co.customer_order_date_at >= DATE_SUB(NOW(), INTERVAL 1 YEAR)
                         GROUP BY b.book_isbn
                         ORDER BY totalSales DESC
                         LIMIT 100;
        """, nativeQuery = true)
    List<BestSellerProjection> findYearlyBestSellers();

    // 카테고리별 베스트셀러 (일주일간, 20위까지)
    @Query(value = """
            SELECT b.book_isbn AS isbn, 
                   b.book_title AS bookTitle,
                   a.author_name AS authorName, 
                   p.publisher_name AS publisherName, 
                   bc.category_name AS categoryName, 
                   b.cover_url AS coverUrl,
                   SUM(cod.amount) AS totalSales
                         FROM customer_orders_detail cod
                         JOIN customer_orders co ON cod.customer_order_id = co.customer_order_id
                         JOIN books b ON cod.book_isbn = b.book_isbn
                         JOIN authors a ON b.author_id = a.author_id
                         JOIN publishers p ON b.publisher_id = p.publisher_id
                         JOIN book_categories bc ON b.category_id = bc.category_id
                         WHERE co.customer_order_date_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
                             AND ( bc.category_id = :categoryId OR bc.parent_category_id = :categoryId )
                         GROUP BY b.book_isbn
                         ORDER BY totalSales DESC
                         LIMIT 20;
        """, nativeQuery = true)
    List<BestSellerProjection> findBestSellersByCategory(@Param("categoryId") Long categoryId);

    // 판매 수량 차트
    // 카테고리별
    @Query(value = """
            SELECT bc.category_name AS categoryName, 
                   SUM(cod.amount) AS totalSales
                         FROM customer_orders_detail cod
                         JOIN customer_orders co ON cod.customer_order_id = co.customer_order_id
                         JOIN books b ON cod.book_isbn = b.book_isbn
                         JOIN book_categories bc ON b.category_id = bc.category_id
                         WHERE co.customer_order_date_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
                         GROUP BY bc.category_name
                         ORDER BY totalSales DESC;
        """, nativeQuery = true)
    List<SalesQuantityStatisticsProjection> findSalesQuantityByCategory();

    // 할인항목별
    @Query(value = """
            SELECT dp.policy_title AS policyTitle, 
                   SUM(cod.amount) AS totalSales
                         FROM customer_orders_detail cod
                         JOIN customer_orders co ON cod.customer_order_id = co.customer_order_id
                         JOIN books b ON cod.book_isbn = b.book_isbn
                         JOIN discount_policies dp ON b.discount_policy_id = dp.policy_id
                         WHERE co.customer_order_date_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
                         GROUP BY dp.policy_title
                         ORDER BY totalSales DESC;
        """, nativeQuery = true)
    List<SalesQuantityStatisticsProjection> findSalesQuantityByDiscountPolicy();

    // 지점별
    @Query(value = """
            SELECT br.branch_name AS branchName, 
                   SUM(co.customer_order_total_amount) AS totalSales
                         FROM customer_orders co
                         JOIN branches br ON co.branch_id = br.branch_id
                         WHERE co.customer_order_date_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
                         GROUP BY br.branch_name
                         ORDER BY totalSales DESC;
        """, nativeQuery = true)
    List<SalesQuantityStatisticsProjection> findSalesQuantityByBranch();

}
