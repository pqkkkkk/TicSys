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
import com.example.ticsys.order.dto.OrderDto;
import com.example.ticsys.order.dto.request.CreateOrderRequest;
import com.example.ticsys.order.dto.response.CreateOrderResponse;
import com.example.ticsys.order.dto.response.GetOrdersResponse;
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
    public OrderDto GetOrderById(int id, String includeStr) {
        try{
            OrderDto orderDto = new OrderDto();
            Order order = orderDao.GetOrderById(id);
            orderDto.setOrder(order);

            if(includeStr != null){
                if(includeStr.contains("event")) {
                    Map<String, Object> event = publicEventService.GetEventByRequiredFieldsList(List.of("id", "name","bannerPath", "date", "time", "location","organizerId"), order.getEventId());
                    orderDto.setEvent(event);
                }
                if(includeStr.contains("ticketOfOrders") && includeStr.contains("ticket")) {
                    List<TicketOfOrder> ticketOfOrders = ticketOfOrderDao.GetTicketsOfOrder(id);
                    List<Map<String, Object>> ticketInfos = new ArrayList<>();

                    for (TicketOfOrder ticketOfOrder : ticketOfOrders)
                    {
                        Map<String, Object> ticketInfo = publicEventService.GetTicketByRequiredFieldsList(List.of("id", "name", "price", "service"), ticketOfOrder.getTicketId());
                        ticketInfos.add(ticketInfo);
                    }
                    orderDto.setTicketOfOrders(ticketOfOrders);
                    orderDto.setTicketInfos(ticketInfos);
                }
                else if (includeStr.contains("ticketOfOrders"))
                {
                    List<TicketOfOrder> ticketOfOrders = ticketOfOrderDao.GetTicketsOfOrder(id);
                    orderDto.setTicketOfOrders(ticketOfOrders);
                    orderDto.setTicketInfos(null);
                }
            }
            return orderDto;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public GetOrdersResponse GetOrders(String includeStr, Map<String,Object> filterMap) {
        try{

            List<OrderDto> orderDtos = new ArrayList<>();
            List<Order> orders = orderDao.GetOrders(null, null, null, null, 0);

            if(includeStr != null){
               for(Order order : orders){
                OrderDto orderDto = new OrderDto();
                orderDto.setOrder(order);

                if(includeStr.contains("event")) {
                    Map<String, Object> event = publicEventService.GetEventByRequiredFieldsList(List.of("id", "name","bannerPath", "date", "time", "location","organizerId"), order.getEventId());
                    orderDto.setEvent(event);
                }
                if(includeStr.contains("ticketOfOrders") && includeStr.contains("ticket")) {
                    List<TicketOfOrder> ticketOfOrders = ticketOfOrderDao.GetTicketsOfOrder(order.getId());
                    List<Map<String, Object>> ticketInfos = new ArrayList<>();

                    for (TicketOfOrder ticketOfOrder : ticketOfOrders)
                    {
                        Map<String, Object> ticketInfo = publicEventService.GetTicketByRequiredFieldsList(List.of("id", "name", "price", "service"), ticketOfOrder.getTicketId());
                        ticketInfos.add(ticketInfo);
                    }
                    orderDto.setTicketOfOrders(ticketOfOrders);
                    orderDto.setTicketInfos(ticketInfos);
                }
                else if (includeStr.contains("ticketOfOrders"))
                {
                    List<TicketOfOrder> ticketOfOrders = ticketOfOrderDao.GetTicketsOfOrder(order.getId());
                    orderDto.setTicketOfOrders(ticketOfOrders);
                    orderDto.setTicketInfos(null);
                }

                orderDtos.add(orderDto);
               }
            }
            return GetOrdersResponse.builder().message("success").orderDtos(orderDtos).build();
        }
        catch (Exception e)
        {
            return GetOrdersResponse.builder().orderDtos(null).message(e.getMessage()).build();
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
