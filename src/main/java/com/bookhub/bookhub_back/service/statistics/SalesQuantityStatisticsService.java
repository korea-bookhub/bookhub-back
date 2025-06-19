package com.bookhub.bookhub_back.service.statistics;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.projection.BestSellerProjection;
import com.bookhub.bookhub_back.dto.statistics.projection.SalesQuantityStatisticsProjection;

import java.util.List;

public interface SalesQuantityStatisticsService {
    ResponseDto<List<BestSellerProjection>> getTop100BestSellers();

    ResponseDto<List<BestSellerProjection>> getWeeklyBestSellers();

    ResponseDto<List<BestSellerProjection>> getMonthlyBestSellers();

    ResponseDto<List<BestSellerProjection>> getYearlyBestSellers();

    ResponseDto<List<BestSellerProjection>> getBestSellersByCategory(String categoryName);

    ResponseDto<List<SalesQuantityStatisticsProjection>> getSalesQuantityByCategory();

    ResponseDto<List<SalesQuantityStatisticsProjection>> getSalesQuantityByDiscountPolicy();

    ResponseDto<List<SalesQuantityStatisticsProjection>> getSalesQuantityByBranch();
}
