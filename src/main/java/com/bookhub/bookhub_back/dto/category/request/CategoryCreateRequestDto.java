package com.bookhub.bookhub_back.dto.category.request;

import com.bookhub.bookhub_back.common.enums.CategoryType;
import com.bookhub.bookhub_back.entity.BookCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryCreateRequestDto {
    private BookCategory parentCategoryId;

    @NotBlank(message = "카테고리명은 필수 입력 값입니다.")
    private String categoryName;

    @NotBlank(message = "카테고리 레벨은 필수 입력 값입니다.")
    private int categoryLevel;

    @NotBlank(message = "카테고리타입은 필수 입력 값입니다.")
    private CategoryType categoryType;

    private int categoryOrder;
}
