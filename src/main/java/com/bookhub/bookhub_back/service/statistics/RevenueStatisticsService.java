package com.bookhub.bookhub_back.service.statistics;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.response.revenue.WeekdayRevenueResponseDto;

import java.util.List;

public interface RevenueStatisticsService {
    ResponseDto<List<WeekdayRevenueResponseDto>> getWeekdayRevenue(int year, int quarter);
}
