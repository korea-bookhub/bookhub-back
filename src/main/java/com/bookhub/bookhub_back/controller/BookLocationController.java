package com.bookhub.bookhub_back.controller;

import com.bookhub.bookhub_back.common.constants.ApiMappingPattern;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.location.request.LocationCreateRequestDto;
import com.bookhub.bookhub_back.dto.location.request.LocationUpdateRequestDto;
import com.bookhub.bookhub_back.dto.location.response.LocationCreateResponseDto;
import com.bookhub.bookhub_back.dto.location.response.LocationDetailResponseDto;
import com.bookhub.bookhub_back.dto.location.response.LocationResponseDto;
import com.bookhub.bookhub_back.dto.location.response.LocationUpdateResponseDto;
import com.bookhub.bookhub_back.service.BookLocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booklocation")
@RequiredArgsConstructor
public class BookLocationController {
    private final BookLocationService bookLocationService;

    //1)책의 위치 생성
    @PostMapping(ApiMappingPattern.MANAGER_API+"/location/{branchId}")
    public ResponseEntity<ResponseDto<LocationCreateResponseDto>> createLocation(
            @PathVariable Long branchId,
            @Valid @RequestBody LocationCreateRequestDto dto){
        ResponseDto<LocationCreateResponseDto> location = bookLocationService.createLocation(branchId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    //2)책의 위치 수정
    @PutMapping
    public ResponseEntity<ResponseDto<LocationUpdateResponseDto>> updateLocation(
            @PathVariable Long branchId,
            @PathVariable Long locationId,
            @Valid @RequestBody LocationUpdateRequestDto dto){
        ResponseDto<LocationUpdateResponseDto> changeLocation = bookLocationService.updateLocation(branchId,locationId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(changeLocation);
    }

    //3)책을 검색하여 하여 책 리스트 반환
    @GetMapping(ApiMappingPattern.COMMON_API+"/location/{branchId}/search")
    public ResponseEntity<ResponseDto<List<LocationResponseDto>>> searchBranchBooksByTitle(
            @PathVariable Long branchId,
            @RequestParam String bookTitle){
        ResponseDto<List<LocationResponseDto>> books = bookLocationService.searchBranchBooksByTitle(branchId,bookTitle);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    //4)해당 책을 클릭하여 위치 반환
    @GetMapping(ApiMappingPattern.COMMON_API+"/location/{branchId}/search/{bookIsbn}")
    public ResponseEntity<ResponseDto<LocationDetailResponseDto>> getLocation(
            @PathVariable Long branchId,
            @PathVariable String bookIsbn){
        ResponseDto<LocationDetailResponseDto> location = bookLocationService.getLocation(branchId,bookIsbn);
        return ResponseEntity.status(HttpStatus.OK).body(location);
    }



}
