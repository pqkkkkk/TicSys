package com.example.ticsys.order.model;

import java.sql.Time;
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
public class Order {
    int id;
    int price;
    String createdBy;
    int eventId;
    LocalDate dateCreatedAt;
    Time timeCreatedAt;
    String status;
    int promotionId;
}
