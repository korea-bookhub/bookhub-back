package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderApproveRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import com.bookhub.bookhub_back.dto.purchaseOrderApproval.response.PurchaseOrderApprovalResponseDto;
import com.bookhub.bookhub_back.service.PurchaseOrderApprovalService;
import com.bookhub.bookhub_back.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API + ApiMappingPattern.ADMIN_API + "/purchase-order-approval")
@RequiredArgsConstructor
public class PurchaseOrderApprovalController {
    private final PurchaseOrderApprovalService purchaseOrderApprovalService;
    private final PurchaseOrderService purchaseOrderService;

    // 1-1) 발주 요청서 수정 - 발주 승인 기능 (승인 또는 승인 거절) -> purchaseOrderApproval 생성
    @PutMapping("/approval/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<PurchaseOrderResponseDto>> approvePurchaseOrder(
            @AuthenticationPrincipal String loginId,
            @PathVariable Long purchaseOrderId,
            @RequestBody PurchaseOrderApproveRequestDto dto
            ){
        ResponseDto<PurchaseOrderResponseDto> response = purchaseOrderService.approvePurchaseOrder(loginId, purchaseOrderId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 1-2) 발주 요청서 기능 - 발주 승인 취소 (승인 -> 승인 거절) --- 고민중

    // 2) 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<PurchaseOrderApprovalResponseDto>>> getAllPurchaseOrderApprovals() {
        ResponseDto<List<PurchaseOrderApprovalResponseDto>> response = purchaseOrderApprovalService.getAllPurchaseOrderApprovals();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 3) 단건 조회
    @GetMapping("/{purchaseOrderApprovalId}")
    public ResponseEntity<ResponseDto<PurchaseOrderApprovalResponseDto>> getPurchaseOrderApprovalById(
            @PathVariable Long purchaseOrderApprovalId
    ) {
        ResponseDto<PurchaseOrderApprovalResponseDto> response = purchaseOrderApprovalService.getPurchaseOrderApprovalById(purchaseOrderApprovalId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 4) 조회 조건으로 조회 - 승인 담당자, 승인 일자, 승인 여부
    // 4-1) 승인 담당자로 조회
    @GetMapping("/employee-name/{employeeName}")
    public ResponseEntity<ResponseDto<List<PurchaseOrderApprovalResponseDto>>> getPurchaseOrderApprovalByEmployeeName(
            @PathVariable String employeeName
    ) {
        ResponseDto<List<PurchaseOrderApprovalResponseDto>> response = purchaseOrderApprovalService.getPurchaseOrderApprovalByEmployeeName(employeeName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 4-2) 승인 일자로 조회
    @GetMapping("/date")
    public ResponseEntity<ResponseDto<List<PurchaseOrderApprovalResponseDto>>> getPurchaseOrderApprovalByCreatedAt(
            @RequestParam LocalDate startedDate,
            @RequestParam LocalDate endedDate
    ) {
        ResponseDto<List<PurchaseOrderApprovalResponseDto>> response = purchaseOrderApprovalService.getPurchaseOrderApprovalByCreatedAt(startedDate, endedDate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 4-3) 승인 여부로 조회
    @GetMapping("/is-approved/{isApproved}")
    public ResponseEntity<ResponseDto<List<PurchaseOrderApprovalResponseDto>>> getPurchaseOrderApprovalByIsApproved(
            @PathVariable boolean isApproved
    ) {
        ResponseDto<List<PurchaseOrderApprovalResponseDto>> response = purchaseOrderApprovalService.getPurchaseOrderApprovalByIsApproved(isApproved);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 4-4) 승인 일자 - 1개월, 3개월, 6개월 단위로 조회 --- 고민중
}
