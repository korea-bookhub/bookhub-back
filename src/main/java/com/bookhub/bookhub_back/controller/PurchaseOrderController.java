package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorCreateRequestDto;
import com.bookhub.bookhub_back.dto.author.response.AuthorResponseDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderCreateRequestDto;
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
@RequestMapping("api/v1/auth/purchaseOrder")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    // 1) 발주 요청서 작성
    @PostMapping
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> createPurchaseOrder(
            @AuthenticationPrincipal Long employeeId,
            @Valid @RequestBody PurchaseOrderCreateRequestDto dto
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.createPurchaseOrder(employeeId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 발주 요청서 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> getAllPurchaseOrders(
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.getAllPurchaseOrders();
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

    // 4) 발주 요청서 조회 - 조회 기준: 발주담당사원, isbn, 승인 상태
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<PurchaseOrderResponseDto>>> getPurchaseOrderByEmployeeNameAndIsbnAndPurchaseOrderStatus(
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) PurchaseOrderStatus purchaseOrderStatus
            // api 형식: GET /search?category=xxx&writer=yyy
    ) {
        ResponseDto<List<PurchaseOrderResponseDto>> response = purchaseOrderService.getPurchaseOrderByEmployeeNameAndIsbnAndPurchaseOrderStatus(employeeName, isbn, purchaseOrderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 발주 일자로 조회 - 고민중

    // 5) 발주 요청서 수정 - 발주량
    @PutMapping("/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<PurchaseOrderResponseDto>> updatePurchaseOrder(
            int purchaseAmount,
            @PathVariable Long purchaseOrderId
    ) {
        ResponseDto<PurchaseOrderResponseDto> response = purchaseOrderService.updatePurchaseOrder(purchaseAmount, purchaseOrderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 7) 발주 요청서 수정 - 발주 승인 기능

    // 8) 발주 요청서 삭제
    @DeleteMapping("/{purchaseOrderId}")
    public ResponseEntity<ResponseDto<Void>> deletePurchaseOrder(
            @PathVariable Long purchaseOrderId
    ) {
        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
        return ResponseEntity.noContent().build();
    }

}
