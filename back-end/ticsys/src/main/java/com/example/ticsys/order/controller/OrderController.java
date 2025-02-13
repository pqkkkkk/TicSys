package com.example.ticsys.order.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticsys.order.dto.OrderDto;
import com.example.ticsys.order.dto.request.CreateOrderRequest;
import com.example.ticsys.order.dto.response.CreateOrderResponse;
import com.example.ticsys.order.dto.response.GetOrdersResponse;
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
    public ResponseEntity<GetOrdersResponse> GetOrders(@RequestParam(value = "include" ,required = false) String includeStr,
                                                    @RequestParam(required = false) String userIdStr,
                                                    @RequestParam(required = false) String eventIdStr,
                                                    @RequestParam(required = false) String dateCreatedAtStr,
                                                    @RequestParam(required = false) String timeCreatedAtStr,
                                                    @RequestParam(required = false) String statusStr){
        
        Map<String,Object> filterMap = new HashMap<>();
        if(userIdStr != null){
            filterMap.put("userId", userIdStr);
        }
        if(eventIdStr != null){
            int eventId = Integer.parseInt(eventIdStr);
            filterMap.put("eventId", eventId);
        }
        if(dateCreatedAtStr != null){
            LocalDate dateCreatedAt = LocalDate.parse(dateCreatedAtStr);
            filterMap.put("dateCreatedAt", dateCreatedAt);
        }
        if(timeCreatedAtStr != null){
            Time timeCreatedAt = Time.valueOf(timeCreatedAtStr);
            filterMap.put("timeCreatedAt", timeCreatedAt);
        }
        if(statusStr != null){
            filterMap.put("status", statusStr);
        }

        GetOrdersResponse result = orderService.GetOrders(includeStr, filterMap);

        if (result.getMessage().equals("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
            
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> GetOrderById(@PathVariable int id, @RequestParam(value = "include" ,required = false) String includeStr){
        OrderDto result = orderService.GetOrderById(id, includeStr);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

}
