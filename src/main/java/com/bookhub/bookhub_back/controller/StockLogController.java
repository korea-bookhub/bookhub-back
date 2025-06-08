package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.stock.response.StockListResponseDto;
import com.bookhub.bookhub_back.dto.stock.response.StockUpdateResponseDto;
import com.bookhub.bookhub_back.service.StockLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API + ApiMappingPattern.ADMIN_API + "/stock-logs")
@RequiredArgsConstructor
public class StockLogController {
    private final StockLogService stockLogService;

    //branch 의 로그 전체 조회



    //branch + 유형별 로그 전체조회

    //branch + 시간별 로그 조회

    //employeeId로 로그 전체 조회

    //특정 책에 대한 로그 전체 조회
}
