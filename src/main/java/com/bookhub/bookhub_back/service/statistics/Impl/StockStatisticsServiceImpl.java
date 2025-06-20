package com.bookhub.bookhub_back.service.statistics.Impl;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.response.stocks.BranchStockBarChartDto;
import com.bookhub.bookhub_back.dto.statistics.response.stocks.CategoryStockResponseDto;
import com.bookhub.bookhub_back.dto.statistics.response.stocks.TimeStockChartResponseDto;
import com.bookhub.bookhub_back.dto.statistics.response.stocks.ZeroStockResponseDto;
import com.bookhub.bookhub_back.service.statistics.StocksStaticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockStatisticsServiceImpl implements StocksStaticsService {
    @Override
    public ResponseDto<List<BranchStockBarChartDto>> getBranchStockBarChart(int year, int month) {
        return null;
    }

    @Override
    public ResponseDto<List<TimeStockChartResponseDto>> getTimeStockStatistics(int year) {
        return null;
    }

    @Override
    public ResponseDto<List<ZeroStockResponseDto>> getZeroStockBooks() {
        return null;
    }

    @Override
    public ResponseDto<List<CategoryStockResponseDto>> getCategoryStocks() {
        return null;
    }
}
