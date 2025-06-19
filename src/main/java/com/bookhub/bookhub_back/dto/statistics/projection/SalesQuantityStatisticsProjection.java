package com.bookhub.bookhub_back.dto.statistics.projection;

public interface SalesQuantityStatisticsProjection {
    Long getTotalSales();
    String getCategoryName();
    String getPolicyTitle();
    String getBranchName();
}
