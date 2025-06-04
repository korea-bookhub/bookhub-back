package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorCreateRequestDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorRequestDto;
import com.bookhub.bookhub_back.dto.author.response.AuthorResponseDto;
import com.bookhub.bookhub_back.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth/author") // 권한 수정 예정
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    // 작가 등록 (여러건 동시 등록)
    @PostMapping
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> createAuthor(
            @Valid @RequestBody AuthorCreateRequestDto dto
    ) {
        return authorService.createAuthor(dto);
    }

    // 작가 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // 작가 단건 조회
    @GetMapping("/{authorId}")
    public ResponseEntity<ResponseDto<AuthorResponseDto>> getAuthorById(
            @PathVariable Long authorId
    ) {
        return authorService.getAuthorById(authorId);
    }

    // 작가 이름으로 조회 (동명이인까지 조회)
    @GetMapping("/authorName/{authorName}")
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthorsByName(
            @PathVariable String authorName
    ) {
        return authorService.getAllAuthorsByName(authorName);
    }

    // 작가 수정
    @PutMapping("/{authorId}")
    public ResponseEntity<ResponseDto<AuthorResponseDto>> updateAuthor(
            @PathVariable Long authorId,
            @Valid @RequestBody AuthorRequestDto dto
    ) {
        return authorService.updateAuthor(authorId, dto);
    }

    // 작가 삭제
    @DeleteMapping("/{authorId}")
    public ResponseEntity<ResponseDto<Void>> deleteAuthor(
            @PathVariable Long authorId
    ) {
        return authorService.deleteAuthor(authorId);
    }
}
