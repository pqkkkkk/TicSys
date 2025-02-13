package com.example.ticsys.order.service;


import java.util.Map;

import com.example.ticsys.order.dto.OrderDto;
import com.example.ticsys.order.dto.request.CreateOrderRequest;
import com.example.ticsys.order.dto.response.CreateOrderResponse;
import com.example.ticsys.order.dto.response.GetOrdersResponse;

public interface OrderService {
    public CreateOrderResponse CreateOrder(CreateOrderRequest createOrderRequest);
    public OrderDto GetOrderById(int id, String includeStr);
    public GetOrdersResponse GetOrders(String includeStr, Map<String, Object> filterMap);
    public String ReserveOrder(int id);
}
