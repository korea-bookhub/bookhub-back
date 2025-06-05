package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.book.response.BookResponseDto;
import com.bookhub.bookhub_back.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BOOK_API)
@RequiredArgsConstructor
public class CommonBookController {
    private final BookService bookService;

    // 1. 책 통합 검색
    @GetMapping("/search")
    public ResponseDto<List<BookResponseDto>> searchBook(
            @RequestParam String keyword) {
        return bookService.searchBook(keyword);
    }

}
