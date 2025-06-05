package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.stock.request.StockUpdateRequestDto;
import com.bookhub.bookhub_back.dto.stock.response.StockListResponseDto;
import com.bookhub.bookhub_back.dto.stock.response.StockUpdateResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface StockService {
    ResponseDto<StockUpdateResponseDto> lossStock(Long stockId, @Valid StockUpdateRequestDto dto);

    ResponseDto<List<StockListResponseDto>> searchByBookIsbn(String bookIsbn);

    ResponseDto<List<StockListResponseDto>> searchByBookTitle(String bookTitle);

    ResponseDto<List<StockListResponseDto>> searchByBranch(String branchName);
}
