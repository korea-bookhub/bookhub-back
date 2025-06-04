package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/purchaseOrder")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    // 사용자 정보에서 받아오기: @Authenti~

    // 1) 발주 요청서 작성
    // 2) 발주 요청서 전체 조회
    // 3) 발주 요청서 단건 조회
    // 4) 발주 요청서 수정 - 내용
    // 5) 발주 요청서 수정 - 발주 승인 및 발주 취소
    // 6) 발주 요청서 삭제
}
