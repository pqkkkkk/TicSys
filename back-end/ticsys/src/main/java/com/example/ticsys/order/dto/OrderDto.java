package com.example.ticsys.order.dto;

import java.util.List;
import java.util.Map;

import com.example.ticsys.order.model.Order;
import com.example.ticsys.order.model.TicketOfOrder;

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
public class OrderDto {
    Order order;
    Map<String,Object> event;
    List<TicketOfOrder> ticketOfOrders;
    List<Map<String,Object>> ticketInfos;
}
