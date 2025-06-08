package com.bookhub.bookhub_back.dto.stock.response;

import com.bookhub.bookhub_back.common.enums.StockActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockLogResponseDto {
    private Long logId;
    private StockActionType type;
}
