package com.bookhub.bookhub_back.dto.branch.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponseDto {
    private String branchName;
    private String branchLocation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
