package com.bookhub.bookhub_back.service.statistics;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.projection.BookSalesProjection;

import java.util.List;

public interface SalesQuantityStatisticsService {
    ResponseDto<List<BookSalesProjection>> getTop100BestSellers();

    ResponseDto<List<BookSalesProjection>> getWeeklyBestSellers();

    ResponseDto<List<BookSalesProjection>> getMonthlyBestSellers();

    ResponseDto<List<BookSalesProjection>> getYearlyBestSellers();

    ResponseDto<List<BookSalesProjection>> getBestSellersByCategory(String categoryName);
}
