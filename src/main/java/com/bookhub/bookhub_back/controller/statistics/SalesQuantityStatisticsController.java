package com.bookhub.bookhub_back.controller.statistics;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.projection.BookSalesProjection;
import com.bookhub.bookhub_back.service.statistics.SalesQuantityStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//판매 수량별 통계 컨트롤러
@RestController
@RequestMapping(ApiMappingPattern.BASIC_API + ApiMappingPattern.ADMIN_API + ("/statistics/sales-quantity"))
@RequiredArgsConstructor
public class SalesQuantityStatisticsController {
    private final SalesQuantityStatisticsService salesQuantityStatisticsService;
    // 총합 베스트셀러
    @GetMapping("/bestseller")
    public ResponseEntity<ResponseDto<List<BookSalesProjection>>> getTop100BestSellers() {
        ResponseDto<List<BookSalesProjection>> response = salesQuantityStatisticsService.getTop100BestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 기간별 베스트 셀러 -- 일주일, 한달, 일년
    // 일주일
    @GetMapping("/bestseller/weekly")
    public ResponseEntity<ResponseDto<List<BookSalesProjection>>> getWeeklyBestSellers() {
        ResponseDto<List<BookSalesProjection>> response = salesQuantityStatisticsService.getWeeklyBestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 한달
    @GetMapping("/bestseller/monthly")
    public ResponseEntity<ResponseDto<List<BookSalesProjection>>> getMonthlyBestSellers() {
        ResponseDto<List<BookSalesProjection>> response = salesQuantityStatisticsService.getMonthlyBestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 일년
    @GetMapping("/bestseller/yearly")
    public ResponseEntity<ResponseDto<List<BookSalesProjection>>> getYearlyBestSellers() {
        ResponseDto<List<BookSalesProjection>> response = salesQuantityStatisticsService.getYearlyBestSellers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 카테고리별 (일주일간)
    @GetMapping("/bestseller/category/{categoryName}")
    public ResponseEntity<ResponseDto<List<BookSalesProjection>>> getBestSellersByCategory(
            @PathVariable String categoryName
    ) {
        ResponseDto<List<BookSalesProjection>> response = salesQuantityStatisticsService.getBestSellersByCategory(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 밀리언셀러 (100만부 이상 판매)

    /* 아래는 차트로 표현 */
    // 카테고리별
    // 할인항목별
    // 지점별
}
