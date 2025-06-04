package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessage;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.publisher.request.PublisherRequestDto;
import com.bookhub.bookhub_back.dto.publisher.response.PublisherResponseDto;
import com.bookhub.bookhub_back.entity.Publisher;
import com.bookhub.bookhub_back.repository.PublisherRepository;
import com.bookhub.bookhub_back.service.PublisherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    //repository와 연결
    private final PublisherRepository publisherRepository;

    //1)출판사 생성
    @Override
    public ResponseDto<PublisherResponseDto> createPublisher(PublisherRequestDto dto) {
        PublisherResponseDto responseDto = null;
        Publisher newPublisher = Publisher.builder()
                .publisherName(dto.getPublisherName())
                .build();

        Publisher saved = publisherRepository.save(newPublisher);

        responseDto = PublisherResponseDto.builder()
                .publisherId(saved.getPublisherId())
                .publisherName(saved.getPublisherName())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    //2)출판사 수정
    @Override
    @Transactional
    public ResponseDto<PublisherResponseDto> updatePublisher(Long publisherId, PublisherRequestDto dto) {
        PublisherResponseDto responseDto = null;
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.NO_EXIST_ID + publisherId));

        publisher.setPublisherName(dto.getPublisherName());

        Publisher updatedPublisher = publisherRepository.save(publisher);

        responseDto = PublisherResponseDto.builder()
                .publisherId(updatedPublisher.getPublisherId())
                .publisherName(updatedPublisher.getPublisherName())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    //3)출판사 전체 조회
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<PublisherResponseDto>> getAllPublishers() {
        List<PublisherResponseDto> responseDtos = null;

        List<Publisher> publishers = publisherRepository.findAll();

        responseDtos = publishers.stream()
                .map(publisher -> PublisherResponseDto.builder()
                        .publisherId(publisher.getPublisherId())
                        .publisherName(publisher.getPublisherName())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos);
    }

    //4)출판사 단건 조회
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<PublisherResponseDto> getPublisherById(Long publisherId) {
        PublisherResponseDto responseDto = null;
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.NO_EXIST_ID + publisherId));

        responseDto = PublisherResponseDto.builder()
                .publisherId(publisher.getPublisherId())
                .publisherName(publisher.getPublisherName())
                .build();


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto);
    }

    //5)출판사 삭제
    @Override
    public ResponseDto<Void> deletePublisher(Long publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.NO_EXIST_ID + publisherId));

        publisherRepository.deleteById(publisherId);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
}
