package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorCreateRequestDto;
import com.bookhub.bookhub_back.dto.author.request.AuthorRequestDto;
import com.bookhub.bookhub_back.dto.author.response.AuthorResponseDto;
import com.bookhub.bookhub_back.entity.Author;
import com.bookhub.bookhub_back.repository.AuthorRepository;
import com.bookhub.bookhub_back.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    // 작가 등록 (여러건 동시 등록)
    @Override
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> createAuthor(AuthorCreateRequestDto dto) {
        List<AuthorResponseDto> responseDtos = null;

        List<AuthorRequestDto> requestDtos = dto.getAuthors();

        List<Author> authors = requestDtos.stream()
                        .map(requestDto -> Author.builder()
                                .authorName(requestDto.getAuthorName())
                                .authorEmail(requestDto.getAuthorEmail())
                                .build())
                        .collect(Collectors.toList());

        List<Author> savedAuthors = authorRepository.saveAll(authors);

        responseDtos = savedAuthors.stream()
                .map(author -> AuthorResponseDto.builder()
                        .authorId(author.getAuthorId())
                        .authorName((author.getAuthorName()))
                        .authorEmail(author.getAuthorEmail())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    // 작가 전체 조회
    @Override
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthors() {
        List<AuthorResponseDto> responseDtos = null;

        List<Author> authors = authorRepository.findAll();

        responseDtos = authors.stream()
                .map(author -> AuthorResponseDto.builder()
                        .authorId(author.getAuthorId())
                        .authorName((author.getAuthorName()))
                        .authorEmail(author.getAuthorEmail())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    // 작가 단건 조회
    @Override
    public ResponseEntity<ResponseDto<AuthorResponseDto>> getAuthorById(Long authorId) {
        AuthorResponseDto responseDto = null;

//        Author author = authorRepository.getAuthorByAuthorId(authorId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + authorId));

        responseDto = AuthorResponseDto.builder()
                .authorId(author.getAuthorId())
                .authorName(author.getAuthorName())
                .authorEmail(author.getAuthorEmail())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    // 작가 이름으로 조회 (동명이인까지 조회)
    @Override
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> getAllAuthorsByName(String authorName) {
        List<AuthorResponseDto> responseDtos = null;

        List<Author> authors = authorRepository.findAllByAuthorName(authorName);

        responseDtos = authors.stream()
                .map(author -> AuthorResponseDto.builder()
                        .authorId(author.getAuthorId())
                        .authorName((author.getAuthorName()))
                        .authorEmail(author.getAuthorEmail())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    // 작가 수정
    @Override
    public ResponseEntity<ResponseDto<AuthorResponseDto>> updateAuthor(Long authorId, AuthorRequestDto dto) {
        AuthorResponseDto responseDto = null;

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + authorId));

        author.setAuthorName(dto.getAuthorName());
        author.setAuthorEmail(dto.getAuthorEmail());

        Author updatedAuthor = authorRepository.save(author);

        responseDto = AuthorResponseDto.builder()
                .authorId(updatedAuthor.getAuthorId())
                .authorName(updatedAuthor.getAuthorName())
                .authorEmail(updatedAuthor.getAuthorEmail())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    // 작가 삭제
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FAILED + authorId));

        authorRepository.delete(author);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        //"[작가이름: " + author.getAuthorName() + ", 작가이메일: " + author.getAuthorEmail() + "] 작가의 정보 삭제를 성공하였습니다."
    }



}
