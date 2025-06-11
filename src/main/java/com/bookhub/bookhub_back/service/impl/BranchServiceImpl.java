package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.common.constants.ResponseCode;
import com.bookhub.bookhub_back.common.constants.ResponseMessageKorean;
import com.bookhub.bookhub_back.common.util.DateUtils;
import com.bookhub.bookhub_back.dto.ResponseDto;
import com.bookhub.bookhub_back.dto.branch.request.BranchCreateRequestDto;
import com.bookhub.bookhub_back.dto.branch.request.BranchUpdateRequestDto;
import com.bookhub.bookhub_back.dto.branch.response.BranchResponseDto;
import com.bookhub.bookhub_back.entity.Branch;
import com.bookhub.bookhub_back.repository.BranchRepository;
import com.bookhub.bookhub_back.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    public ResponseDto<BranchResponseDto> createBranch(BranchCreateRequestDto dto) {
        BranchResponseDto responseDto = null;
        Branch branch = null;

        if (branchRepository.existsByBranchName(dto.getBranchName())) {
            throw new EntityNotFoundException();
        }

        branch = Branch.builder()
            .branchName(dto.getBranchName())
            .branchLocation(dto.getBranchLocation())
            .build();

        Branch newBranch = branchRepository.save(branch);

        responseDto = BranchResponseDto.builder()
            .branchName(newBranch.getBranchName())
            .branchLocation(newBranch.getBranchLocation())
            .createdAt(DateUtils.format(branch.getCreatedAt()))
            .updatedAt(DateUtils.format(branch.getUpdatedAt()))
            .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<List<BranchResponseDto>> getBranchesByLocation(String branchLocation) {
        List<BranchResponseDto> responseDtos = null;
        List<Branch> branches = null;

        if (branchLocation == null || branchLocation.isBlank()) {
            throw new IllegalArgumentException();
        } else if (branchLocation.equals("전체")) {
            branches = branchRepository.findAll();
        } else {
            branches = branchRepository.findByBranchLocationContaining(branchLocation);
        }

        responseDtos = branches.stream()
            .map(branch -> BranchResponseDto.builder()
                .branchId(branch.getBranchId())
                .branchName(branch.getBranchName())
                .branchLocation(branch.getBranchLocation())
                .createdAt(DateUtils.format(branch.getCreatedAt()))
                .updatedAt(DateUtils.format(branch.getUpdatedAt()))
                .build())
            .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDtos);
    }

    @Override
    public ResponseDto<BranchResponseDto> updateBranch(Long branchId, BranchUpdateRequestDto dto) {
        BranchResponseDto responseDto = null;

        Branch branch = branchRepository.findById(branchId)
            .orElseThrow(IllegalArgumentException::new);

        if (dto.getBranchName().isBlank() && dto.getBranchLocation().isBlank()) {
            throw new IllegalArgumentException();
        }

        if (dto.getBranchName().isBlank()) {
            branch.setBranchLocation(dto.getBranchLocation());
        }
        if (dto.getBranchLocation().isBlank()) {
            branch.setBranchName(dto.getBranchName());
        }

        if (!dto.getBranchName().isEmpty() && !dto.getBranchLocation().isEmpty()) {
            branch.setBranchName(dto.getBranchName());
            branch.setBranchLocation(dto.getBranchLocation());
        }


        Branch updateBranch = branchRepository.save(branch);

        responseDto = BranchResponseDto.builder()
            .branchName(updateBranch.getBranchName())
            .branchLocation(updateBranch.getBranchLocation())
            .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, responseDto);
    }

    @Override
    public ResponseDto<Void> deleteBranch(Long branchId) {
        Branch branch = null;

        branch = branchRepository.findById(branchId)
            .orElseThrow(IllegalArgumentException::new);

        branchRepository.delete(branch);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessageKorean.SUCCESS, null);
    }
}
