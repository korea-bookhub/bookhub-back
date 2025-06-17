package com.bookhub.bookhub_back.service.statistics.Impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.projection.BookSalesProjection;
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
    public ResponseDto<List<BookSalesProjection>> getTop100BestSellers() {
        List<BookSalesProjection> responseDtos = salesQuantityStatisticsRepository.findTop100BestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BookSalesProjection>> getWeeklyBestSellers() {
        List<BookSalesProjection> responseDtos = salesQuantityStatisticsRepository.findWeeklyBestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BookSalesProjection>> getMonthlyBestSellers() {
        List<BookSalesProjection> responseDtos = salesQuantityStatisticsRepository.findMonthlyBestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BookSalesProjection>> getYearlyBestSellers() {
        List<BookSalesProjection> responseDtos = salesQuantityStatisticsRepository.findYearlyBestSellers();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<List<BookSalesProjection>> getBestSellersByCategory(String categoryName) {
        List<BookSalesProjection> responseDtos = salesQuantityStatisticsRepository.findBestSellersByCategory(categoryName);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }
}
