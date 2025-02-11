package com.example.ticsys.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticsys.order.dto.request.CreateOrderRequest;
import com.example.ticsys.order.dto.request.GetFilteredOrdersRequest;
import com.example.ticsys.order.dto.response.CreateOrderResponse;
import com.example.ticsys.order.dto.response.GetDetailOrderResponse;
import com.example.ticsys.order.dto.response.GetFilteredOrdersResponse;
import com.example.ticsys.order.model.Order;
import com.example.ticsys.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> ReserveOrder(@PathVariable int id){
        String result = orderService.ReserveOrder(id);
        if (result.equals("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    @PostMapping
    public ResponseEntity<CreateOrderResponse> CreateOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        CreateOrderResponse result = orderService.CreateOrder(createOrderRequest);
        if(result.getMessage().equals("success"))
        {
            return ResponseEntity.ok(result);
        }
        else
        {
            return ResponseEntity.badRequest().body(result);
        }
    }
    @GetMapping
    public ResponseEntity<GetFilteredOrdersResponse> GetFilteredOrders(@RequestBody GetFilteredOrdersRequest request){
        GetFilteredOrdersResponse result = orderService.GetFilteredOrders(request);
        if (result.getMessage().equals("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
            
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> GetOrderById(@PathVariable int id){
        Order result = orderService.GetOrderById(id);
        if (result == null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    @GetMapping("/{id}/detail")
    public ResponseEntity<GetDetailOrderResponse> GetDetailOrderById(@PathVariable int id){
        GetDetailOrderResponse result = orderService.GetDetailOrderById(id);
        if (result.getMessage().equals("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

}
