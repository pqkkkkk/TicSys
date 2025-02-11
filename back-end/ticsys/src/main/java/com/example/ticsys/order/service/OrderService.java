package com.example.ticsys.order.service;


import com.example.ticsys.order.dto.request.CreateOrderRequest;
import com.example.ticsys.order.dto.request.GetFilteredOrdersRequest;
import com.example.ticsys.order.dto.response.CreateOrderResponse;
import com.example.ticsys.order.dto.response.GetDetailOrderResponse;
import com.example.ticsys.order.dto.response.GetFilteredOrdersResponse;
import com.example.ticsys.order.model.Order;

public interface OrderService {
    public CreateOrderResponse CreateOrder(CreateOrderRequest createOrderRequest);
    public Order GetOrderById(int id);
    public GetDetailOrderResponse GetDetailOrderById(int id);
    public GetFilteredOrdersResponse GetFilteredOrders(GetFilteredOrdersRequest getFilteredOrdersRequest);
    public String ReserveOrder(int id);
}
