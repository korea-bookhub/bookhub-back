package com.bookhub.bookhub_back.dto.statistics.response.revenue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeekdayRevenueResponseDto {
    private String weekday;
    private Long total;
}
