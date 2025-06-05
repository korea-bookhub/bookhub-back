package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.book.request.BookCreateRequestDto;
import com.bookhub.bookhub_back.dto.book.request.BookUpdateRequestDto;
import com.bookhub.bookhub_back.dto.book.response.BookResponseDto;
import com.bookhub.bookhub_back.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_BOOK_API)
@RequiredArgsConstructor
public class AdminBookController {
    private final BookService bookService;

    // 1. 책 생성
    @PostMapping
    public ResponseEntity<ResponseDto<BookResponseDto>> createBook(
            @Valid @RequestBody BookCreateRequestDto dto) {
        ResponseDto<BookResponseDto> book = bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // 2. 책 수정
    @PutMapping("/{isbn}")
    public ResponseDto<BookResponseDto> updateBook(
            @PathVariable String isbn,
            @RequestBody BookUpdateRequestDto dto) {
        return bookService.updateBook(isbn, dto);
    }

    // 3. 책 삭제
    @DeleteMapping("/{isbn}")
    public ResponseDto<Void> deleteBook(@PathVariable String isbn) {
        return bookService.deleteBook(isbn);
    }

}
