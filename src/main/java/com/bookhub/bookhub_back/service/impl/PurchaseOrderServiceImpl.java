package com.bookhub.bookhub_back.service.impl;

import com.bookhub.bookhub_back.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    // 지점ID로 지점명 받아오는 방법
    // Branch branch = branchRepository.findById(branchid);
    // String branchName = branch.getBranchName();
}
