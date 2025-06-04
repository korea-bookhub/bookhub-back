package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.publisher.request.PublisherRequestDto;
import com.bookhub.bookhub_back.dto.publisher.response.PublisherResponseDto;
import com.bookhub.bookhub_back.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.PUBLISHER_API)
@RequiredArgsConstructor
public class PublisherController {
    //서비스 단이랑 연결
    private final PublisherService publisherService;

    //1) 출판사 생성
    @PostMapping
    public ResponseEntity<ResponseDto<PublisherResponseDto>> createPublisher(
            @Valid @RequestBody PublisherRequestDto dto){
        ResponseDto<PublisherResponseDto> publisher = publisherService.createPublisher(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisher);
    }

    //2)출판사 수정
    @PutMapping("/{publisherId}")
    public ResponseEntity<ResponseDto<PublisherResponseDto>> updatePublisher(
            @PathVariable Long publisherId,
            @Valid @RequestBody PublisherRequestDto dto){
        ResponseDto<PublisherResponseDto> changedPublisher = publisherService.updatePublisher(publisherId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(changedPublisher);
    }

    //3)전체 조회하기
    @GetMapping
    public ResponseEntity<ResponseDto<List<PublisherResponseDto>>> getAllPublishers(){
        ResponseDto<List<PublisherResponseDto>> publishers = publisherService.getAllPublishers();
        return ResponseEntity.status(HttpStatus.OK).body(publishers);
    }

    //4)단건 조회하기
    @GetMapping("/{publisherId}")
    public ResponseEntity<ResponseDto<PublisherResponseDto>> getpublisherById(
            @PathVariable Long publisherId){
        ResponseDto<PublisherResponseDto> publisher = publisherService.getPublisherById(publisherId);
        return ResponseEntity.status(HttpStatus.OK).body(publisher);
    }

    //4)출판사 삭제하기
    @DeleteMapping("/publisherId")
    public ResponseEntity<ResponseDto<Void>> deletePublisher(@PathVariable Long publisherId){
        ResponseDto<Void> responseDto = publisherService.deletePublisher(publisherId);
        return ResponseEntity.noContent().build();
    }


}
