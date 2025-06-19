package com.bookhub.bookhub_back.service.statistics.Impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.projection.BestSellerProjection;
import com.bookhub.bookhub_back.dto.statistics.projection.SalesQuantityStatisticsProjection;
import com.bookhub.bookhub_back.repository.statistics.SalesQuantityStatisticsRepository;
import com.bookhub.bookhub_back.service.statistics.SalesQuantityStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesQuantityStatisticsServiceImpl implements SalesQuantityStatisticsService {
    private final SalesQuantityStatisticsRepository salesQuantityStatisticsRepository;

    @Override
    public ResponseDto<List<BestSellerProjection>> getTop100BestSellers() {
        List<BestSellerProjection> responseDtos = salesQuantityStatisticsRepository.findTop100BestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BestSellerProjection>> getWeeklyBestSellers() {
        List<BestSellerProjection> responseDtos = salesQuantityStatisticsRepository.findWeeklyBestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BestSellerProjection>> getMonthlyBestSellers() {
        List<BestSellerProjection> responseDtos = salesQuantityStatisticsRepository.findMonthlyBestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BestSellerProjection>> getYearlyBestSellers() {
        List<BestSellerProjection> responseDtos = salesQuantityStatisticsRepository.findYearlyBestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BestSellerProjection>> getBestSellersByCategory(String categoryName) {
        List<BestSellerProjection> responseDtos = salesQuantityStatisticsRepository.findBestSellersByCategory(categoryName);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<SalesQuantityStatisticsProjection>> getSalesQuantityByCategory() {
        List<SalesQuantityStatisticsProjection> responseDtos = salesQuantityStatisticsRepository.findSalesQuantityByCategory();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<SalesQuantityStatisticsProjection>> getSalesQuantityByDiscountPolicy() {
        List<SalesQuantityStatisticsProjection> responseDtos = salesQuantityStatisticsRepository.findSalesQuantityByDiscountPolicy();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<SalesQuantityStatisticsProjection>> getSalesQuantityByBranch() {
        List<SalesQuantityStatisticsProjection> responseDtos = salesQuantityStatisticsRepository.findSalesQuantityByBranch();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }
}
