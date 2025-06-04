package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.publisher.request.PublisherRequestDto;
import com.bookhub.bookhub_back.dto.publisher.response.PublisherResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PublisherService {

    ResponseDto<PublisherResponseDto> createPublisher(@Valid PublisherRequestDto dto);

    ResponseDto<PublisherResponseDto> updatePublisher(Long publisherId, @Valid PublisherRequestDto dto);

    ResponseDto<List<PublisherResponseDto>> getAllPublishers();

    ResponseDto<PublisherResponseDto> getPublisherById(Long publisherId);

    ResponseDto<Void> deletePublisher(Long publisherId);
}
