package com.bookhub.bookhub_back.dto.statistics.projection;

import java.time.LocalDate;

public interface SalesQuantityStatisticsProjection {
    Long getTotalSales();
    LocalDate getOrderDate();
    Integer getOrderMonth();
    String getCategoryName();
    String getPolicyTitle();
    String getBranchName();
}
