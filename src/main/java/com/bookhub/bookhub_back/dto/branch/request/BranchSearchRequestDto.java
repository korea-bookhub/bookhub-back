package com.bookhub.bookhub_back.dto.branch.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchSearchRequestDto {
    private String branchName;
}
