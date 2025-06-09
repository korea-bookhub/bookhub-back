package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorCreateRequestDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorRequestDto;
import com.bookhub.bookhub_back.dto.author.response.AuthorResponseDto;
import com.bookhub.bookhub_back.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.BASIC_API + ApiMappingPattern.ADMIN_API + "/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    // 작가 등록 (여러건 동시 등록)
    @PostMapping
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> createAuthor(
            @Valid @RequestBody AuthorCreateRequestDto dto
    ) {
        ResponseDto<List<AuthorResponseDto>> response = authorService.createAuthor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 작가 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthors() {
        ResponseDto<List<AuthorResponseDto>> response = authorService.getAllAuthors();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 작가 단건 조회
    @GetMapping("/{authorId}")
    public ResponseEntity<ResponseDto<AuthorResponseDto>> getAuthorById(
            @PathVariable Long authorId
    ) {
        ResponseDto<AuthorResponseDto> response = authorService.getAuthorById(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 작가 이름으로 조회 (동명이인까지 조회)
    @GetMapping("/author-name/{authorName}")
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthorsByName(
            @PathVariable String authorName
    ) {
        ResponseDto<List<AuthorResponseDto>> response = authorService.getAllAuthorsByName(authorName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 작가 수정
    @PutMapping("/{authorId}")
    public ResponseEntity<ResponseDto<AuthorResponseDto>> updateAuthor(
            @PathVariable Long authorId,
            @Valid @RequestBody AuthorRequestDto dto
    ) {
        ResponseDto<AuthorResponseDto> response = authorService.updateAuthor(authorId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 작가 삭제
    @DeleteMapping("/{authorId}")
    public ResponseEntity<ResponseDto<Void>> deleteAuthor(
            @PathVariable Long authorId
    ) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }
}
