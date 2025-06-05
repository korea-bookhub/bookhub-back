package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderCreateRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PurchaseOrderService {
    ResponseDto<List<PurchaseOrderResponseDto>> createPurchaseOrder(Long employeeId, @Valid PurchaseOrderCreateRequestDto dto);

    ResponseDto<List<PurchaseOrderResponseDto>> getAllPurchaseOrders();

    ResponseDto<PurchaseOrderResponseDto> updatePurchaseOrder(int purchaseAmount, Long purchaseOrderId);

    ResponseDto<Void> deletePurchaseOrder(Long purchaseOrderId);

    ResponseDto<PurchaseOrderResponseDto> getPurchaseOrderById(Long purchaseOrderId);

    ResponseDto<List<PurchaseOrderResponseDto>> getPurchaseOrderByEmployeeNameAndIsbnAndPurchaseOrderStatus(String employeeName, String isbn, PurchaseOrderStatus purchaseOrderStatus);
}
