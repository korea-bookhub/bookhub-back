package com.bookhub.bookhub_back.service.statistics.Impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.response.revenue.WeekdayRevenueResponseDto;
import com.bookhub.bookhub_back.repository.statistics.RevenueStatisticsRepository;
import com.bookhub.bookhub_back.service.statistics.RevenueStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RevenueStatisticsServiceImpl implements RevenueStatisticsService {

    private final RevenueStatisticsRepository revenueStatisticsRepository;

    @Override
    public ResponseDto<List<WeekdayRevenueResponseDto>> getWeekdayRevenue(int year, int quarter) {
        int startMonth = (quarter -1) *3+1;
        int endMonth = startMonth+2;

        //Repository에서 매출 집계 쿼리 호출
        List<Object[]> result = revenueStatisticsRepository.findRevenueGroupedByWeekday(year, startMonth, endMonth);

        String[] weekdays = {"월","화","수","목","금","토","일"};
        Map<String, Long> salesMap = new LinkedHashMap<>();
        for(String weekday : weekdays) salesMap.put(weekday,0L);
        for(Object[] row : result){
            String weekday = (String) row[0];
            Long total = ((Number) row[1]).longValue();
            salesMap.put(weekday,total);
        }

        List<WeekdayRevenueResponseDto> responsedtos = new ArrayList<>();
        for(String weekday:weekdays){
            responsedtos.add(new WeekdayRevenueResponseDto(weekday, salesMap.get(weekday)));

        }
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,responsedtos);
    }
}




































































