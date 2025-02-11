package com.example.ticsys.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.example.ticsys.event.PublicEventService;
import com.example.ticsys.order.dao.order.IOrderDao;
import com.example.ticsys.order.dao.ticketoforder.ITicketOfOrderDao;
import com.example.ticsys.order.dto.request.CreateOrderRequest;
import com.example.ticsys.order.dto.request.GetFilteredOrdersRequest;
import com.example.ticsys.order.dto.response.CreateOrderResponse;
import com.example.ticsys.order.dto.response.GetDetailOrderResponse;
import com.example.ticsys.order.dto.response.GetFilteredOrdersResponse;
import com.example.ticsys.order.model.Order;
import com.example.ticsys.order.model.TicketOfOrder;

@Service
public class OrderServiceImpl implements OrderService  {
    private final IOrderDao orderDao;
    private final ITicketOfOrderDao ticketOfOrderDao;
    private final PublicEventService publicEventService;

    public OrderServiceImpl(IOrderDao orderDao, ITicketOfOrderDao ticketOfOrderDao, PublicEventService publicEventService) {
        this.orderDao = orderDao;
        this.ticketOfOrderDao = ticketOfOrderDao;
        this.publicEventService = publicEventService;
    }

    @Override
    @Transactional
    public CreateOrderResponse CreateOrder(CreateOrderRequest createOrderRequest) {
       try{
            createOrderRequest.getOrder().setStatus("PENDING");
            int orderId = orderDao.CreateOrder(createOrderRequest.getOrder());
            if(orderId == -1)
            {
                throw new Exception("Order creation failed");
            }

            for(TicketOfOrder ticketOfOrder : createOrderRequest.getTicketOfOrders())
            {
                ticketOfOrder.setOrderId(orderId);
                if(!ticketOfOrderDao.CreateTicketOfOrder(ticketOfOrder))
                {
                    throw new Exception("Ticket creation failed");
                }
            }

            return CreateOrderResponse.builder().message("success").orderId(orderId).build();
       }
       catch (Exception e)
       {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return CreateOrderResponse.builder().message(e.getMessage()).build();
       }
    }

    @Override
    public Order GetOrderById(int id) {
        try{
            Order order = orderDao.GetOrderById(id);
            if(order == null)
            {
                throw new Exception("Order not found");
            }
            return order;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public GetFilteredOrdersResponse GetFilteredOrders(GetFilteredOrdersRequest getFilteredOrdersRequest) {
        try{
            List<Order> orders = orderDao.GetOrders(getFilteredOrdersRequest.getUserId(),
                    getFilteredOrdersRequest.getDateCreated(),
                    getFilteredOrdersRequest.getTimeCreated(),
                     getFilteredOrdersRequest.getStatus());

            return GetFilteredOrdersResponse.builder().orders(orders).message("success").build();
        }
        catch (Exception e)
        {
            return GetFilteredOrdersResponse.builder().message(e.getMessage()).build();
        }
    }

    @Override
    public GetDetailOrderResponse GetDetailOrderById(int id) {
        try{
            Order order = orderDao.GetOrderById(id);
            List<TicketOfOrder> ticketOfOrders = ticketOfOrderDao.GetTicketsOfOrder(id);
            List<Map<String, Object>> ticketInfos = new ArrayList<>();

            List<String> requiredFields = List.of("id", "name", "price", "service");
            for (TicketOfOrder ticketOfOrder : ticketOfOrders)
            {
                Map<String, Object> ticketInfo = publicEventService.GetTicketByRequiredFieldsList(requiredFields, ticketOfOrder.getTicketId());
                ticketInfos.add(ticketInfo);
            }

            return GetDetailOrderResponse.builder().message("success").order(order)
                                                    .ticketInfos(ticketInfos)     
                                                    .ticketOfOrders(ticketOfOrders).build();
        }
        catch (Exception e)
        {
            return GetDetailOrderResponse.builder().message(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public String ReserveOrder(int id) {
        try{
            if(!orderDao.UpdateOrderStatus(id, "PAID")){
                throw new Exception("Order status update failed");
            }

            List<TicketOfOrder> ticketOfOrders = ticketOfOrderDao.GetTicketsOfOrder(id);
            for(TicketOfOrder ticketOfOrder : ticketOfOrders)
            {
                Map<String, Object> ticketInfo = publicEventService.GetTicketByRequiredFieldsList(List.of("quantity"), ticketOfOrder.getTicketId());
                int quantity = (int) ticketInfo.get("quantity");
                int afterQuantity = quantity - ticketOfOrder.getQuantity();

                if(afterQuantity < 0)
                {
                    throw new Exception("Ticket quantity is not enough");
                }

                int updateTicketResult = publicEventService.UpdateTicketByRequiredFieldsList(Map.of("quantity", afterQuantity), ticketOfOrder.getTicketId());
                if(updateTicketResult == 0)
                {
                    throw new Exception("Ticket quantity update failed");
                }
            }

            return "success";
        }
        catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return e.getMessage();
        }
    }
    

}
