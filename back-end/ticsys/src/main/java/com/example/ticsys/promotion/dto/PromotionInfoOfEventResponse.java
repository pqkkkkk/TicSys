package com.example.ticsys.promotion.dto;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PromotionInfoOfEventResponse {
    String message;
    List<PromotionInfo> promotionInfos;
}
