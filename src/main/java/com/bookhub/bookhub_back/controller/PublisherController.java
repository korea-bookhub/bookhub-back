package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.publisher.request.PublisherCreateRequestDto;
import com.bookhub.bookhub_back.dto.publisher.request.PublisherUpdateRequestDto;
import com.bookhub.bookhub_back.dto.publisher.response.PublisherCreateResponseDto;
import com.bookhub.bookhub_back.dto.publisher.response.PublisherResponseDto;
import com.bookhub.bookhub_back.dto.publisher.response.PublisherUpdateResponseDto;
import com.bookhub.bookhub_back.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.PUBLISHER_API)
@RequiredArgsConstructor
public class PublisherController {


}
