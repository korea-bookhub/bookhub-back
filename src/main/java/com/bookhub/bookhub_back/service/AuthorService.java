package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorCreateRequestDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorRequestDto;
import com.bookhub.bookhub_back.dto.author.response.AuthorResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorService {
    ResponseEntity<ResponseDto<List<AuthorResponseDto>>> createAuthor(@Valid AuthorCreateRequestDto dto);

    ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthors();

    ResponseEntity<ResponseDto<AuthorResponseDto>> updateAuthor(Long authorId, AuthorRequestDto dto);

    ResponseEntity<ResponseDto<Void>> deleteAuthor(Long authorId);

    ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthorsByName(String authorName);

    ResponseEntity<ResponseDto<AuthorResponseDto>> getAuthorById(Long authorId);
}
