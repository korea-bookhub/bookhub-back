package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.branch.request.BranchCreateRequestDto;
import com.bookhub.bookhub_back.dto.branch.response.BranchResponseDto;
import com.bookhub.bookhub_back.entity.Branch;
import com.bookhub.bookhub_back.repository.BranchRepository;
import com.bookhub.bookhub_back.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    public ResponseDto<BranchResponseDto> createBranch(BranchCreateRequestDto dto) {
        BranchResponseDto responseDto = null;
        Branch branch = null;

        if (branchRepository.existsByBranchName(dto.getBranchName())) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_BRANCH, ResponseMessageKorean.DUPLICATED_BRANCH);
        }

        branch = Branch.builder()
            .branchName(dto.getBranchName())
            .branchLocation(dto.getBranchLocation())
            .build();

        Branch newBranch = branchRepository.save(branch);

        responseDto = BranchResponseDto.builder()
            .branchName(newBranch.getBranchName())
            .branchLocation(newBranch.getBranchLocation())
            .createdAt(newBranch.getCreatedAt())
            .updatedAt(newBranch.getUpdatedAt())
            .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDto);
    }
}
