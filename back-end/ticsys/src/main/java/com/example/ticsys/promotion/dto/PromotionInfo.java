package com.example.ticsys.promotion.dto;

import com.example.ticsys.promotion.model.Promotion;

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
public class PromotionInfo {
    int reduction;
    Promotion promotion;
}
