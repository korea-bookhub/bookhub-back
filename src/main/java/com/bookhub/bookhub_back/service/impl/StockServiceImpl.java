package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.stock.request.StockUpdateRequestDto;
import com.bookhub.bookhub_back.dto.stock.response.StockListResponseDto;
import com.bookhub.bookhub_back.dto.stock.response.StockUpdateResponseDto;
import com.bookhub.bookhub_back.entity.Stock;
import com.bookhub.bookhub_back.entity.StockLog;
import com.bookhub.bookhub_back.repository.StockLogRepository;
import com.bookhub.bookhub_back.repository.StockRepository;
import com.bookhub.bookhub_back.service.StockService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;//재고
    private final StockLogRepository stockLogRepository;//재고 로그 입력


    //재고 업데이트
    @Override
    public ResponseDto<StockUpdateResponseDto> lossStock(Long stockId, StockUpdateRequestDto dto) {

        if(!(dto.getType().equals("Loss"))){
            return ResponseDto.fail(ResponseCode.FAIL, ResponseMessage.FAILED);
        }

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.NO_EXIST_ID +stockId));


//        //만약 Book이랑 branch가 동시에 일치하는 Stock이 없을때
//        Stock stock = stockRepository.findByBookIsbnAndBranchId(dto.getBookIsbn(), dto.getBranchId())
//                .orElseGet(() -> {
//                    Stock newStock = Stock.builder()
//                            .bookIsbn(dto.getBookIsbn())
//                            .branchId(dto.getBranchId())
//                            .bookAmount(0L)
//                            .build();
//                    return stockRepository.save(newStock);
//                });

        if (stock.getBookAmount() < dto.getAmount()) {
            throw new IllegalArgumentException(ResponseCode.DATA_INTEGRITY_VIOLATION);
        }

        // type에 따라 amount가 바뀜
        Long updatedAmount=0L;
        updatedAmount = stock.getBookAmount() - dto.getAmount();

        stock.setBookAmount(updatedAmount);
        stockRepository.save(stock);

        StockUpdateResponseDto responseDto = StockUpdateResponseDto.builder()
                .stockId(stock.getStockId())
                .branchName(stock.getBranchId().getBranchName())
                .type(dto.getType())
                .bookTitle(stock.getBookIsbn().getBookTitle())
                .amount(dto.getAmount())
                .bookAmount(stock.getBookAmount())
                .build();

        StockLog log = StockLog.builder()
                .stockActionType(dto.getType())
                .employeeId(dto.getEmployeeId())
                .bookIsbn(dto.getBookIsbn())
                .branchId(dto.getBranchId())
                .amount(dto.getAmount())
                .bookAmount(updatedAmount)
                .description(dto.getDescription())
                .build();

        stockLogRepository.save(log);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<StockListResponseDto>> searchByBookIsbn(String bookIsbn) {

        List<StockListResponseDto> responseDtos = null;
        List<Stock> stocks = stockRepository.findByBookIsbn_Isbn(bookIsbn);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(ResponseCode.NO_EXIST_ID + bookIsbn);
        }

        responseDtos = stocks.stream()
                .map(stock -> StockListResponseDto.builder()
                        .branchName(stock.getBranchId().getBranchName())
                        .bookTitle(stock.getBookIsbn().getBookTitle())
                        .amount(stock.getBookAmount())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<StockListResponseDto>> searchByBookTitle(String bookTitle) {
        List<StockListResponseDto> responseDtos = null;
        List<Stock> stocks = stockRepository.findByBookIsbn_BookTitleContaining(bookTitle);

        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            return ResponseDto.fail(ResponseCode.REQUIRED_FIELD_MISSING,ResponseMessage.REQUIRED_FIELD_MISSING);
        }

        responseDtos = stocks.stream()
                .map(stock -> StockListResponseDto.builder()
                        .branchName(stock.getBranchId().getBranchName())
                        .bookTitle(stock.getBookIsbn().getBookTitle())
                        .amount(stock.getBookAmount())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<StockListResponseDto>> searchByBranch(String branchName) {
        List<StockListResponseDto> responseDtos = null;
        List<Stock> stocks = stockRepository.findByBranchId_BranchName(branchName);
        if (stocks.isEmpty()) {
            throw new EntityNotFoundException(ResponseCode.NO_EXIST_ID + branchName);
        }

        responseDtos = stocks.stream()
                .map(stock -> StockListResponseDto.builder()
                        .branchName(stock.getBranchId().getBranchName())
                        .bookTitle(stock.getBookIsbn().getBookTitle())
                        .amount(stock.getBookAmount())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }
}
