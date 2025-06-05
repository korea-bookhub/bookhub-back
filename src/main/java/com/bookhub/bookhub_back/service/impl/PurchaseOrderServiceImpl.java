package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderCreateRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import com.bookhub.bookhub_back.entity.Book;
import com.bookhub.bookhub_back.entity.Branch;
import com.bookhub.bookhub_back.entity.Employee;
import com.bookhub.bookhub_back.entity.PurchaseOrder;
import com.bookhub.bookhub_back.repository.BookRepository;
import com.bookhub.bookhub_back.repository.BranchRepository;
import com.bookhub.bookhub_back.repository.EmployeeRepository;
import com.bookhub.bookhub_back.repository.PurchaseOrderRepository;
import com.bookhub.bookhub_back.service.BranchService;
import com.bookhub.bookhub_back.service.PurchaseOrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final EmployeeRepository employeeRepository;
    private final BookRepository bookRepository;

    // 1) 발주 요청서 작성
    @Override
    public ResponseDto<List<PurchaseOrderResponseDto>> createPurchaseOrder(Long employeeId, PurchaseOrderCreateRequestDto dto) {
        List<PurchaseOrderResponseDto> responseDtos = null;
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.AUTHENTICATION_FAIL));

        Branch branch = employee.getBranchId();

        List<PurchaseOrderRequestDto> requestDtos = dto.getPurchaseOrders();

        for(PurchaseOrderRequestDto requestDto: requestDtos) {
            purchaseOrders.add(PurchaseOrder.builder()
                            .purchaseOrderAmount(requestDto.getPurchaseOrderAmount())
                            .purchaseOrderStatus(PurchaseOrderStatus.REQUESTED)
                            .employeeId(employee)
                            .branchId(branch)
                            .bookIsbn(bookRepository.findByIsbn(requestDto.getIsbn())
                                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NO_EXIST_ID)))
                    .build());
        }

        List<PurchaseOrder> savedOrders = purchaseOrderRepository.saveAll(purchaseOrders);

        responseDtos = savedOrders.stream()
                .map(order -> changeToPurchaseOrderResponseDto(order))
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    // 2) 발주 요청서 전체 조회
    @Override
    public ResponseDto<List<PurchaseOrderResponseDto>> getAllPurchaseOrders() {
        List<PurchaseOrderResponseDto> responseDtos = null;

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        responseDtos = purchaseOrders.stream()
                .map(order -> changeToPurchaseOrderResponseDto(order))
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }


    // 단건 조회 by id
    @Override
    public ResponseDto<PurchaseOrderResponseDto> getPurchaseOrderById(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + purchaseOrderId));

        PurchaseOrderResponseDto responseDto = changeToPurchaseOrderResponseDto(purchaseOrder);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    // 발주 요청서 조회 - 조회 기준: 발주담당사원, isbn, 승인 상태
    @Override
    public ResponseDto<List<PurchaseOrderResponseDto>> getPurchaseOrderByEmployeeNameAndIsbnAndPurchaseOrderStatus(String employeeName, String isbn, PurchaseOrderStatus purchaseOrderStatus) {
        List<PurchaseOrderResponseDto> responseDtos = null;
        List<PurchaseOrder> purchaseOrders = null;

        Employee employee = employeeRepository.findByName(employeeName)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));

        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));

        if(employeeName == null && isbn == null && purchaseOrderStatus == null) {
            throw new IllegalArgumentException("조회 조건을 선택하세요");
        } if(isbn == null && purchaseOrderStatus == null) {
            purchaseOrders = purchaseOrderRepository.findByEmployeeId(employee.getEmployeeId());
        } else if (employeeName == null && purchaseOrderStatus == null) {
            purchaseOrders = purchaseOrderRepository.findByBookIsbn(book);
        } else if (employeeName == null && isbn == null) {
            purchaseOrders = purchaseOrderRepository.findByPurchaseOrderStatus(purchaseOrderStatus);
        } else if (purchaseOrderStatus == null) {
            purchaseOrders = purchaseOrderRepository.findByEmployeeIdAndBookIsbn(employee.getEmployeeId(), book);
        } else if (isbn == null) {
            purchaseOrders = purchaseOrderRepository.findByEmployeeIdAndPurchaseOrderStatus(employee.getEmployeeId(), purchaseOrderStatus);
        } else if (employeeName == null) {
            purchaseOrders = purchaseOrderRepository.findByBookIsbnAndPurchaseOrderStatus(book, purchaseOrderStatus);
        } else {
            purchaseOrders = purchaseOrderRepository.findByEmployeeIdAndBookIsbnAndPurchaseOrderStatus(employee.getEmployeeId(), book, purchaseOrderStatus);
        }

        responseDtos = purchaseOrders.stream()
                .map(order -> changeToPurchaseOrderResponseDto(order))
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    // 4) 발주 요청서 수정 - 발주량
    @Override
    public ResponseDto<PurchaseOrderResponseDto> updatePurchaseOrder(int purchaseAmount, Long purchaseOrderId) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + purchaseOrderId));

        purchaseOrder.setPurchaseOrderAmount(purchaseAmount);

        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        PurchaseOrderResponseDto responseDto = changeToPurchaseOrderResponseDto(updatedPurchaseOrder);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    // 6) 발주 요청서 삭제
    @Override
    public ResponseDto<Void> deletePurchaseOrder(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + purchaseOrderId));

        purchaseOrderRepository.delete(purchaseOrder);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    // responseDto 변환 메서드
    public PurchaseOrderResponseDto changeToPurchaseOrderResponseDto(PurchaseOrder order) {

        PurchaseOrderResponseDto responseDto = PurchaseOrderResponseDto.builder()
                .purchaseOrderId(order.getPurchaseOrderId())
                .branchName(order.getBranchId().getBranchName())
                .branchLocation(order.getBranchId().getBranchLocation())
                .employeeName(order.getEmployeeId().getName())
                .isbn(order.getBookIsbn().getIsbn())
                .bookTitle(order.getBookIsbn().getBookTitle())
                .bookPrice(order.getBookIsbn().getBookPrice())
                .purchaseOrderAmount(order.getPurchaseOrderAmount())
                .purchaseOrderDateAt(order.getPurchaseOrderDateAt())
                .purchaseOrderStatus(order.getPurchaseOrderStatus())
                .build();

        return responseDto;

    }

}
