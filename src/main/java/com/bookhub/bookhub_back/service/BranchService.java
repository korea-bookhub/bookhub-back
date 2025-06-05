package com.bookhub.bookhub_back.service;

import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.branch.request.BranchCreateRequestDto;
import com.bookhub.bookhub_back.dto.branch.response.BranchResponseDto;
import jakarta.validation.Valid;

public interface BranchService {
    ResponseDto<BranchResponseDto> createBranch(@Valid BranchCreateRequestDto dto);
}
