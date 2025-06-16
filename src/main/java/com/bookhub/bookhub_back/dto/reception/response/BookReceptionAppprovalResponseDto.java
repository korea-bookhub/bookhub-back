package com.bookhub.bookhub_back.dto.reception.response;

public class BookReceptionAppprovalResponseDto {
    private Long bookReceptionApprovalId;
    private String bookTitle;
    private String bookIsbn;
    private Long purchaseOrderAmount;
    private String branchName;
    private Boolean isReceptionApproved;
    // 수령자랑 수령 날짜는 null일 수도 있어서 없음(수령 확인 누르기 전)
}
