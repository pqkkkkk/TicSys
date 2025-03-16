package com.example.ticsys.event.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.event.dao.event.IEventDao;
import com.example.ticsys.event.dao.event.query.EventQuerySqlDao;
import com.example.ticsys.event.dao.ticket.ITicketDao;
import com.example.ticsys.event.dto.EventDto;
import com.example.ticsys.event.dto.TimelyEventDataDto;
import com.example.ticsys.event.dto.request.EventRequest;
import com.example.ticsys.event.dto.response.GetEventsResponse;
import com.example.ticsys.event.dto.response.TimelyEventRevenueResponse;
import com.example.ticsys.event.dto.response.TimelyEventTicketCountResponse;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.model.Event;
import com.example.ticsys.event.model.Ticket;
import com.example.ticsys.media.CloudinaryService;
import com.example.ticsys.redis.RedisService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Primary
@Service
@Slf4j
public class EventServiceImplV2 implements EventService {
    private final IEventDao eventDao;
    private final ITicketDao ticketDao;
    private final CloudinaryService cloudinaryService;
    private final EventQuerySqlDao eventQuerySqlDao;
    private final RedisService redisService;
    @Autowired
    public EventServiceImplV2(IEventDao eventDao, ITicketDao ticketDao,
                                 CloudinaryService cloudinaryService, EventQuerySqlDao eventQuerySqlDao,
                                 RedisService redisService) {
        this.eventDao = eventDao;
        this.eventQuerySqlDao = eventQuerySqlDao;
        this.ticketDao = ticketDao;
        this.cloudinaryService = cloudinaryService;
        this.redisService = redisService;
    }
    @Override
    @Transactional
    public EventResponse CreateEvent(EventRequest eventRequest, MultipartFile banner, MultipartFile seatMap) {
        String bannerPath = "";
        String seatMapPath = "";
        try{
            if(banner != null){
                bannerPath = cloudinaryService.uploadFile(banner);
            }
            else{
                bannerPath = "empty";
            }
            if(seatMap != null){
                seatMapPath = cloudinaryService.uploadFile(seatMap);
            }
            else{
                seatMapPath = "empty";
            }

            if(bannerPath.equals("") || seatMapPath.equals("")){
                throw new Exception("Failed to upload file");
            }
            eventRequest.getEvent().setBannerPath(bannerPath.equals("empty") ? null : bannerPath);
            eventRequest.getEvent().setSeatMapPath(seatMapPath.equals("empty") ? null : seatMapPath);
            int eventId = eventDao.CreateEvent(eventRequest.getEvent());
            if(eventId == -1){
                throw new Exception("Failed to create event");
            }

            for(Ticket ticket : eventRequest.getTickets()){
                ticket.setEventId(eventId);
                if(!ticketDao.AddTicket(ticket)){
                    throw new Exception("Failed to create ticket");
                }
            }

            return EventResponse.builder().message("success").build();
        }
        catch (Exception e){
            String deleteBannerResult = cloudinaryService.deleteFile(bannerPath);
            String deleteSeatMapResult = cloudinaryService.deleteFile(seatMapPath);

            log.error("Failed to create event: " + e.getMessage());
            log.error("Delete banner result: " + deleteBannerResult);
            log.error("Delete seat map result: " + deleteSeatMapResult);

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return EventResponse.builder().message(e.getMessage()).build();
        }
    }
    @Override
    public EventDto GetEventById(int id, String includeStr) {
        try{
            Event event = eventDao.GetEventById(id);
            EventDto eventDto = EventDto.builder().event(event).build();

            List<Ticket> tickets = ticketDao.GetTicketsOfEvent(event.getId());

            int minPriceOfTicket = tickets.stream().mapToInt(Ticket::getPrice)
                                    .min()
                                    .orElse(0);

            eventDto.setMinPriceOfTicket(minPriceOfTicket);

            if(includeStr != null && includeStr.contains("tickets")){
                eventDto.setTickets(tickets);
            }

            return eventDto;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    @Override
    public GetEventsResponse GetEvents(String includeStr, Map<String, Object> filterMap) {
        try{
            // if(redisService.getData("events") != null){
            //     log.info("Get events from Redis");
            //     List<EventDto> eventDtos = (List<EventDto>) redisService.getData("events");
            //     return GetEventsResponse.builder().message("success").eventDtos(eventDtos).build();
            // }

            log.info("Get events from database");
            List<EventDto> eventDtos = new ArrayList<>();
            List<Event> events = eventDao.GetEvents(null, null);

            for(Event event : events)
            {
                EventDto eventDto = new EventDto();
                eventDto.setEvent(event);

                List<Ticket> tickets = ticketDao.GetTicketsOfEvent(event.getId());

                int minPriceOfTicket = tickets.stream().mapToInt(Ticket::getPrice)
                                    .min()
                                    .orElse(0);
                eventDto.setMinPriceOfTicket(minPriceOfTicket);

                if(includeStr != null && includeStr.contains("tickets")){
                    eventDto.setTickets(tickets);
                }

                eventDtos.add(eventDto);
            }

            return GetEventsResponse.builder().message("success").eventDtos(eventDtos).build();
        }
        catch (Exception e)
        {
            return GetEventsResponse.builder().message(e.getMessage()).eventDtos(null).build();
        }
    }
    // @PostConstruct
    // public void AddEventsToRedis(){
    //     List<EventDto> eventDtos = new ArrayList<>();
    //     List<Event> events = eventDao.GetEvents(null, null);

    //     for(Event event : events)
    //     {
    //         EventDto eventDto = new EventDto();
    //         eventDto.setEvent(event);

    //         List<Ticket> tickets = ticketDao.GetTicketsOfEvent(event.getId());

    //         int minPriceOfTicket = tickets.stream().mapToInt(Ticket::getPrice)
    //                             .min()
    //                             .orElse(0);
    //         eventDto.setMinPriceOfTicket(minPriceOfTicket);

    //         eventDto.setTickets(tickets);

    //         eventDtos.add(eventDto);
    //     }

    //     redisService.saveData("events", eventDtos);
    // }
    @Override
    public TimelyEventRevenueResponse CountEventRevenueByDate(Integer eventId, String startDate, String endDate) {
        try{
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            if(start.isAfter(end)){
                return TimelyEventRevenueResponse.builder()
                        .totalRevenue(null)
                        .revenues(null)        
                        .message("Invalid date range").build();
            }

            List<TimelyEventDataDto> revenues = eventQuerySqlDao.GetTimelyEventRevenueByEventId(eventId, startDate, endDate);

            Integer totalRevenue = revenues.stream().mapToInt(TimelyEventDataDto::getValue)
                                .sum();
            
            return TimelyEventRevenueResponse.builder()
            .totalRevenue(totalRevenue)
            .revenues(revenues)
            .message("success").build();
        }
        catch(Exception e){
            return TimelyEventRevenueResponse.builder()
                        .totalRevenue(null)
                        .revenues(null)        
                        .message("Error").build();
        }
    }
    @Override
    public TimelyEventTicketCountResponse CountEventTicketCountByDate(Integer eventId, String startDate,
            String endDate) {
        try{
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            if(start.isAfter(end)){
                return TimelyEventTicketCountResponse.builder()
                        .totalTicketCount(null)
                        .ticketCounts(null)        
                        .message("Invalid date range").build();
            }

            List<TimelyEventDataDto> ticketCounts = eventQuerySqlDao.GetTimelyEventTicketCountByEventId(eventId, startDate, endDate);

            Integer totalRevenue = ticketCounts.stream().mapToInt(TimelyEventDataDto::getValue)
                                .sum();
            
            return TimelyEventTicketCountResponse.builder()
            .totalTicketCount(totalRevenue)
            .ticketCounts(ticketCounts)
            .message("success").build();
        }
        catch(Exception e){
            log.error("Error: " + e.getMessage());
            return TimelyEventTicketCountResponse.builder()
                        .totalTicketCount(null)
                        .ticketCounts(null)        
                        .message("Error").build();
        }
    }
}
