package com.bookhub.bookhub_back.dto.category.response;

import com.bookhub.bookhub_back.common.enums.CategoryType;
import com.bookhub.bookhub_back.entity.BookCategory;
import com.bookhub.bookhub_back.entity.DiscountPolicy;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryTreeResponseDto {
    private Long categoryId;
    private String categoryName;
    private int categoryLevel;
    private CategoryType categoryType;
    private int categoryOrder;
    private Boolean isActive;
    private BookCategory parentCategoryId;
    private DiscountPolicy discountPolicyId;
    private List<CategoryTreeResponseDto> subCategories;
}
