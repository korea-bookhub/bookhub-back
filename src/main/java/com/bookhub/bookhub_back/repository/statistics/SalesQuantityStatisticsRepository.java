package com.bookhub.bookhub_back.repository.statistics;

import com.bookhub.bookhub_back.dto.statistics.projection.BookSalesProjection;
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
                         GROUP BY b.book_isbn, a.author_name, p.publisher_name, bc.category_name
                         ORDER BY totalSales DESC
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      LIMIT 100;
        """, nativeQuery = true)
    List<BookSalesProjection> findTop100BestSellers();

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
                         GROUP BY b.book_isbn, a.author_name, p.publisher_name, bc.category_name
                         ORDER BY totalSales DESC
                         LIMIT 100;
        """, nativeQuery = true)
    List<BookSalesProjection> findWeeklyBestSellers();

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
                         GROUP BY b.book_isbn, a.author_name, p.publisher_name, bc.category_name
                         ORDER BY totalSales DESC
                         LIMIT 100;
        """, nativeQuery = true)
    List<BookSalesProjection> findMonthlyBestSellers();

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
                         GROUP BY b.book_isbn, a.author_name, p.publisher_name, bc.category_name
                         ORDER BY totalSales DESC
                         LIMIT 100;
        """, nativeQuery = true)
    List<BookSalesProjection> findYearlyBestSellers();

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
                             AND bc.category_name = :categoryName
                         GROUP BY b.book_isbn, a.author_name, p.publisher_name, bc.category_name
                         ORDER BY totalSales DESC
                         LIMIT 20;
        """, nativeQuery = true)
    List<BookSalesProjection> findBestSellersByCategory(@Param("categoryName") String categoryName);
}
