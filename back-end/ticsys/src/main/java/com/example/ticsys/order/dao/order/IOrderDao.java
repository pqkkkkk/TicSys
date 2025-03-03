package com.example.ticsys.order.dao.order;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.ticsys.order.model.Order;

public interface IOrderDao {
    public int CreateOrder(Order order);
    public Order GetOrderById(int id);
    public List<Order> GetOrders(String userId, LocalDate dateCreated
                        , Time timeCreated, String status, int eventId);
    public boolean UpdateOrderStatus(int id, String status);
    public int UpdateOrder(int id, Map<String, Object> orderValues);
}
