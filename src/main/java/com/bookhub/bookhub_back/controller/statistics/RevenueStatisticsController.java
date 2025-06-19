package com.bookhub.bookhub_back.controller.statistics;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.statistics.response.revenue.WeekdayRevenueResponseDto;
import com.bookhub.bookhub_back.service.statistics.RevenueStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API+ApiMappingPattern.ADMIN_API+"/statistics/revenue")
@RequiredArgsConstructor
public class RevenueStatisticsController {
    private final RevenueStatisticsService revenueStatisticsService;
    //주간 총 매출 금액
    /*
    x축 : 각 지점
    y축 : 총 매출 금액
    받아야 하는 값 (몇주인지 (월-일까지의 값을 계산할 예정
     */

     //요일별 매출 금액
    //x축 : 월화수목금토일
    //y축 : 금액
    // 받아야 하는 값 (분기) - 1분기 : 1-3월/2분기4-6월/3분기7-9월/4분기 10-12월)
    //토글을 통해서 년도와 분기값을 넣으면 해당 기간 내의 모든 요일별 매출을 더해서 출력해준다.
    @GetMapping("/weekday")
    public ResponseEntity<ResponseDto<List<WeekdayRevenueResponseDto>>> getWeekdayRevenue(
            @RequestParam int year,
            @RequestParam int quarter
    ){
        ResponseDto<List<WeekdayRevenueResponseDto>> revenue = revenueStatisticsService.getWeekdayRevenue(year,quarter);
        return ResponseEntity.status(HttpStatus.OK).body(revenue);
    }

    //월간 총 매출 금액 (1월~12월 : x축)
    //현재 월을 기분으로 이전 1년간의 데이터를 조회할 수 있다. (만약 현재가 2025.6/6일이면 2024.6.1부터 2025.5.31까지의 데이터 조회 가능)

    //월간 총 매출 금액(1월 ~12월)
    //연간 총 매출 금액
    //특정 주를 선택할 시에 -> 지점별 총 매출 금액
    //특정 월 선택시에 -> 지점별 총 매출 금액
    //카테고리별 총 매출 비율(파이차트)
    //월별 평균 객단가



}
