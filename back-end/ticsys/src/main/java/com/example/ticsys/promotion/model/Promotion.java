package com.example.ticsys.promotion.model;

import java.time.LocalDate;

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
public class Promotion {
    int id;
    int eventId;
    int minPriceToReach;
    int promoPercent;
    int voucherValue;
    String status;
    String type;
    LocalDate startDate;
    LocalDate endDate;
}
