package com.bookhub.bookhub_back.service.impl;
import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.book.request.BookCreateRequestDto;
import com.bookhub.bookhub_back.dto.book.request.BookUpdateRequestDto;
import com.bookhub.bookhub_back.dto.book.response.BookResponseDto;
import com.bookhub.bookhub_back.entity.Book;
import com.bookhub.bookhub_back.repository.*;
import com.bookhub.bookhub_back.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    @Override
    public ResponseDto<BookResponseDto> createBook(BookCreateRequestDto dto) {
        Book book = Book.builder()
                .isbn(dto.getIsbn())
                .categoryId(bookCategoryRepository.findById(dto.getCategoryId()).orElseThrow())
                .authorId(authorRepository.findById(dto.getAuthorId()).orElseThrow())
                .publisherId(publisherRepository.findById(dto.getPublisherId()).orElseThrow())
                .bookTitle(dto.getBookTitle())
                .bookPrice(dto.getBookPrice())
                .publishedDate(dto.getPublishedDate())
                .coverUrl(dto.getCoverUrl())
                .pageCount(dto.getPageCount())
                .language(dto.getLanguage())
                .description(dto.getDescription())
                .policyId(dto.getPolicyId() != null
                        ? discountPolicyRepository.findById(dto.getPolicyId()).orElseThrow(null)
                        : null
                )
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toDto(book));
    }

    @Override
    public ResponseDto<BookResponseDto> updateBook(String isbn, BookUpdateRequestDto dto) {
        Book book = bookRepository.findById(isbn).orElseThrow();
        book.setBookTitle(dto.getBookTitle());
        book.setBookPrice(dto.getBookPrice());
        book.setPageCount(dto.getPageCount());
        book.setLanguage(dto.getLanguage());
        book.setDescription(dto.getDescription());
        book.setAuthorId(authorRepository.findById(dto.getAuthorId()).orElseThrow());
        book.setCategoryId(bookCategoryRepository.findById(dto.getCategoryId()).orElseThrow());
        book.setPublisherId(publisherRepository.findById(dto.getPublisherId()).orElseThrow());
        book.setPolicyId(dto.getPolicyId() != null
                ? discountPolicyRepository.findById(dto.getPolicyId()).orElseThrow(null)
                : null
        );
        book.setCoverUrl(dto.getCoverUrl());
        book.setPublishedDate(dto.getPublishedDate());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, toDto(book));
    }

    @Override
    public ResponseDto<List<BookResponseDto>> searchBook(String keyword) {
        List<Book> books = bookRepository.searchAllByKeyword(keyword);

        List<BookResponseDto> result = books.stream()
                .map(this::toDto)
                .toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, result);
    }

    @Override
    public ResponseDto<Void> deleteBook(String isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(()-> new IllegalArgumentException("삭제할 책을 찾을 수 없습니다."));
        bookRepository.delete(book);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
    private BookResponseDto toDto(Book book) {
        return BookResponseDto.builder()
                .isbn(book.getIsbn())
                .bookTitle(book.getBookTitle())
                .categoryName(book.getCategoryId().getCategoryName())
                .authorName(book.getAuthorId().getAuthorName())
                .publisherName(book.getPublisherId().getPublisherName())
                .bookPrice(book.getBookPrice())
                .publishedDate(book.getPublishedDate())
                .coverUrl(book.getCoverUrl())
                .pageCount(book.getPageCount())
                .language(book.getLanguage())
                .description(book.getDescription())
                .policyId(
                        book.getPolicyId() != null
                                ? book.getPolicyId().getPolicyId()
                                : null
                )
                .build();
    }
}
