package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.book.request.BookCreateRequestDto;
import com.bookhub.bookhub_back.dto.book.request.BookUpdateRequestDto;
import com.bookhub.bookhub_back.dto.book.response.BookResponseDto;

import java.util.List;

public interface BookService {
    ResponseDto<BookResponseDto> createBook(BookCreateRequestDto dto);
    ResponseDto<BookResponseDto> updateBook(String isbn, BookUpdateRequestDto dto);
    ResponseDto<List<BookResponseDto>> searchBook(String keyword);
    ResponseDto<Void> deleteBook(String isbn);
}
