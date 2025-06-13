package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderCreateRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import com.bookhub.bookhub_back.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API + ApiMappingPattern.MANAGER_API + "/purchase-order")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    // 1) 발주 요청서 작성
    @PostMapping
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> createPurchaseOrder(
            @AuthenticationPrincipal String loginId,
            @Valid @RequestBody PurchaseOrderCreateRequestDto dto
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.createPurchaseOrder(loginId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 발주 요청서 전체 조회 - 사용자 소속 지점 해당 발주서만
    @GetMapping
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> getAllPurchaseOrders(
            @AuthenticationPrincipal String loginId
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.getAllPurchaseOrders(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 3) 발주 요청서 단건 조회 - id로 조회
    @GetMapping("/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<PurchaseOrderResponseDto>> getPurchaseOrderById(
            @PathVariable Long purchaseOrderId
    ) {
        ResponseDto<PurchaseOrderResponseDto> response = purchaseOrderService.getPurchaseOrderById(purchaseOrderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 4) 발주 요청서 조회 - 조회 기준: 발주담당사원, isbn, 승인 상태 (사용자 소속 지점 해당 발주서만)
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> getPurchaseOrderByEmployeeNameAndIsbnAndPurchaseOrderStatus(
            @AuthenticationPrincipal String loginId,
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) PurchaseOrderStatus purchaseOrderStatus
            // api 형식: GET /search?category=xxx&writer=yyy
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.getPurchaseOrderByEmployeeNameAndBookTitleAndPurchaseOrderStatus(loginId, employeeName, bookTitle, purchaseOrderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 발주 일자로 조회 - 고민중

    // 5) 발주 요청서 수정 - 발주량 수정
    @PutMapping("/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<PurchaseOrderResponseDto>> updatePurchaseOrder(
            @RequestBody PurchaseOrderRequestDto dto,
            @PathVariable Long purchaseOrderId
    ) {
        ResponseDto<PurchaseOrderResponseDto> response = purchaseOrderService.updatePurchaseOrder(dto, purchaseOrderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 7) 발주 요청서 삭제
    @DeleteMapping("/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<Void>> deletePurchaseOrder(
            @PathVariable Long purchaseOrderId
    ) {
        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
        return ResponseEntity.noContent().build();
    }

}
