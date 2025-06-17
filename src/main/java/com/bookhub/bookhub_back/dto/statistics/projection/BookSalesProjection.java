package com.bookhub.bookhub_back.dto.statistics.projection;

public interface BookSalesProjection {
    String getIsbn();
    String getBookTitle();
    String getAuthorName();
    String getCategoryName();
    String getPublisherName();
    String getCoverUrl();
    Long getTotalSales();
}
