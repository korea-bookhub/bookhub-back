package com.bookhub.bookhub_back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StockLogController {
//@PreAuthorize("hasRole('ADMIN')")
//    @PutMapping(PUT_AUTHORITY_DEMOTE)


    
    //branch 의 로그 전체 조회

    //branch + 유형별 로그 전체조회

    //branch + 시간별 로그 조회

    //employeeId로 로그 전체 조회

    //특정 책에 대한 로그 전체 조회
}
