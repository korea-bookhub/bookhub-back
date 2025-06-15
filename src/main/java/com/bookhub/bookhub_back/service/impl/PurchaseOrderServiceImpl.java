package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.common.enums.PurchaseOrderStatus;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderApproveRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderCreateRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.request.PurchaseOrderRequestDto;
import com.bookhub.bookhub_back.dto.purchaseOrder.response.PurchaseOrderResponseDto;
import com.bookhub.bookhub_back.entity.*;
import com.bookhub.bookhub_back.repository.BookRepository;
import com.bookhub.bookhub_back.repository.EmployeeRepository;
import com.bookhub.bookhub_back.repository.PurchaseOrderApprovalRepository;
import com.bookhub.bookhub_back.repository.PurchaseOrderRepository;
import com.bookhub.bookhub_back.service.PurchaseOrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderApprovalRepository purchaseOrderApprovalRepository;
    private final EmployeeRepository employeeRepository;
    private final BookRepository bookRepository;

    // 1) 발주 요청서 작성
    @Override
    public ResponseDto<List<PurchaseOrderResponseDto>> createPurchaseOrder(String loginId, PurchaseOrderCreateRequestDto dto) {
        List<PurchaseOrderResponseDto> responseDtos = null;
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();

        Employee employee = employeeRepository.findByLoginId(loginId)
                .orElseThrow(IllegalArgumentException::new);

        Branch branch = employee.getBranchId();

        List<PurchaseOrderRequestDto> requestDtos = dto.getPurchaseOrders();

        for(PurchaseOrderRequestDto requestDto: requestDtos) {
            purchaseOrders.add(PurchaseOrder.builder()
                            .purchaseOrderAmount(requestDto.getPurchaseOrderAmount())
                            .purchaseOrderStatus(PurchaseOrderStatus.REQUESTED)
                            .employeeId(employee)
                            .branchId(branch)
                            .bookIsbn(bookRepository.findByBookTitle(requestDto.getBookTitle())
                                    .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 책입니다.")))
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
    public ResponseDto<List<PurchaseOrderResponseDto>> getAllPurchaseOrders(String loginId) {
        List<PurchaseOrderResponseDto> responseDtos = null;

        Employee employee = employeeRepository.findByLoginId(loginId)
                .orElseThrow(IllegalArgumentException::new);

        Branch branch = employee.getBranchId();

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        responseDtos = purchaseOrders.stream()
                .filter(order -> order.getBranchId() == branch)
                .map(order -> changeToPurchaseOrderResponseDto(order))
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    // 2-1) 발주 요청서 업데이트 (승인 상태 - 요청중 인 발주서만 전체 조회)
    @Override
    public ResponseDto<List<PurchaseOrderResponseDto>> getAllPurchaseOrdersRequested() {
        List<PurchaseOrderResponseDto> responseDtos = null;

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        responseDtos = purchaseOrders.stream()
                .filter(order -> order.getPurchaseOrderStatus() == PurchaseOrderStatus.REQUESTED)
                .map(order -> changeToPurchaseOrderResponseDto(order))
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    // 3) 발주 요청서 단건 조회 - id로 조회
    @Override
    public ResponseDto<PurchaseOrderResponseDto> getPurchaseOrderById(Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + purchaseOrderId));

        PurchaseOrderResponseDto responseDto = changeToPurchaseOrderResponseDto(purchaseOrder);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    // 4) 발주 요청서 조회 - 조회 기준: 발주담당사원, isbn, 승인 상태
    @Override
    public ResponseDto<List<PurchaseOrderResponseDto>> getPurchaseOrderByEmployeeNameAndBookTitleAndPurchaseOrderStatus(
            String loginId, String employeeName, String bookTitle, PurchaseOrderStatus purchaseOrderStatus
    ) {
        List<PurchaseOrderResponseDto> responseDtos = null;
        List<PurchaseOrder> purchaseOrders = null;

        if(employeeName == null && bookTitle == null && purchaseOrderStatus == null) {
            throw new IllegalArgumentException("조회 조건을 선택하세요");
        } if(bookTitle == null && purchaseOrderStatus == null) {
            Employee employee = employeeRepository.findByName(employeeName)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            purchaseOrders = purchaseOrderRepository.findByEmployeeId(employee);
        } else if (employeeName == null && purchaseOrderStatus == null) {
            Book book = bookRepository.findByBookTitle(bookTitle)
                    .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            purchaseOrders = purchaseOrderRepository.findByBookIsbn(book);
        } else if (employeeName == null && bookTitle == null) {
            purchaseOrders = purchaseOrderRepository.findByPurchaseOrderStatus(purchaseOrderStatus);
        } else if (purchaseOrderStatus == null) {
            Employee employee = employeeRepository.findByName(employeeName)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            Book book = bookRepository.findByBookTitle(bookTitle)
                    .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            purchaseOrders = purchaseOrderRepository.findByEmployeeIdAndBookIsbn(employee, book);
        } else if (bookTitle == null) {
            Employee employee = employeeRepository.findByName(employeeName)
                    .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            purchaseOrders = purchaseOrderRepository.findByEmployeeIdAndPurchaseOrderStatus(employee, purchaseOrderStatus);
        } else if (employeeName == null) {
            Book book = bookRepository.findByBookTitle(bookTitle)
                    .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            purchaseOrders = purchaseOrderRepository.findByBookIsbnAndPurchaseOrderStatus(book, purchaseOrderStatus);
        } else {
            Employee employee = employeeRepository.findByName(employeeName)
                    .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            Book book = bookRepository.findByBookTitle(bookTitle)
                    .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_ID));
            purchaseOrders = purchaseOrderRepository.findByEmployeeIdAndBookIsbnAndPurchaseOrderStatus(employee, book, purchaseOrderStatus);
        }

        // 사용자의 지점에 해당하는 purchaseOrders 필터링
        Employee employee = employeeRepository.findByLoginId(loginId)
                .orElseThrow(IllegalArgumentException::new);

        Branch branch = employee.getBranchId();

        List<PurchaseOrder> filteredPurchaseOrder = purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getBranchId() == branch)
                .collect(Collectors.toList());

        responseDtos = filteredPurchaseOrder.stream()
                .map(order -> changeToPurchaseOrderResponseDto(order))
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }


    // 5) 발주 요청서 수정 - 발주량
    @Override
    public ResponseDto<PurchaseOrderResponseDto> updatePurchaseOrder(PurchaseOrderRequestDto dto, Long purchaseOrderId) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + purchaseOrderId));

        purchaseOrder.setPurchaseOrderAmount(dto.getPurchaseOrderAmount());

        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        PurchaseOrderResponseDto responseDto = changeToPurchaseOrderResponseDto(updatedPurchaseOrder);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    // 6) 발주 요청서 수정 - 발주 승인 기능
    @Override
    @Transactional
    public ResponseDto<PurchaseOrderResponseDto> approvePurchaseOrder(String loginId, Long purchaseOrderId, PurchaseOrderApproveRequestDto dto) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + purchaseOrderId));

        Employee employee = employeeRepository.findByLoginId(loginId)
                .orElseThrow(IllegalArgumentException::new);

        if(purchaseOrder.getPurchaseOrderStatus() == PurchaseOrderStatus.REQUESTED) {
            if (dto.getStatus() == PurchaseOrderStatus.APPROVED) {
                purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
            } else if (dto.getStatus() == PurchaseOrderStatus.REJECTED) {
                purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.REJECTED);
            } else {
                throw new IllegalArgumentException("APPROVED 또는 REJECTED 중 하나를 입력하세요.");
            }
        } else {
            throw new IllegalArgumentException("이미 승인/승인 거절된 요청건 입니다.");
        }

        PurchaseOrder approvedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        PurchaseOrderResponseDto responseDto = changeToPurchaseOrderResponseDto(approvedPurchaseOrder);

        // 발주 승인 로그 생성
        PurchaseOrderApproval pOA = PurchaseOrderApproval.builder()
                .employeeId(employee)
                .purchaseOrderId(purchaseOrder)
                .isApproved(purchaseOrder.getPurchaseOrderStatus() == PurchaseOrderStatus.APPROVED ? true : false)
                .build();

        purchaseOrderApprovalRepository.save(pOA);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }


    // 7) 발주 요청서 삭제
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
                .purchaseOrderPrice((order.getBookIsbn().getBookPrice())*(order.getPurchaseOrderAmount()))
                .purchaseOrderDateAt(order.getPurchaseOrderDateAt())
                .purchaseOrderStatus(order.getPurchaseOrderStatus())
                .build();

        return responseDto;

    }

}
