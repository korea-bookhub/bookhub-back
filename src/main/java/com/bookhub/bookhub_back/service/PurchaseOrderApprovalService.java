package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.purchaseOrderApproval.response.PurchaseOrderApprovalResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseOrderApprovalService {

    ResponseDto<List<PurchaseOrderApprovalResponseDto>> getAllPurchaseOrderApprovals();

    ResponseDto<PurchaseOrderApprovalResponseDto> getPurchaseOrderApprovalById(Long id);

    ResponseDto<List<PurchaseOrderApprovalResponseDto>> getPurchaseOrderApprovalByEmployeeName(String employeeName);

    ResponseDto<List<PurchaseOrderApprovalResponseDto>> getPurchaseOrderApprovalByIsApproved(boolean isApproved);

    ResponseDto<List<PurchaseOrderApprovalResponseDto>> getPurchaseOrderApprovalByCreatedAt(LocalDate startedDate, LocalDate endedDate);
}
