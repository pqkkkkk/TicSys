package com.example.ticsys.order.dto.response;

import java.util.List;

import com.example.ticsys.order.model.Order;

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
public class GetFilteredOrdersResponse {
    List<Order> orders;
    String message;
}
