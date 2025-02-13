package com.example.ticsys.event.service;


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
import com.example.ticsys.event.dao.ticket.ITicketDao;
import com.example.ticsys.event.dto.EventDto;
import com.example.ticsys.event.dto.request.EventRequest;
import com.example.ticsys.event.dto.response.GetEventsResponse;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.model.Event;
import com.example.ticsys.event.model.Ticket;
import com.example.ticsys.media.CloudinaryService;

@Primary
@Service
public class EventServiceImplV2 implements EventService {
    private final IEventDao eventDao;
    private final ITicketDao ticketDao;
    private final CloudinaryService cloudinaryService;
    @Autowired
    public EventServiceImplV2(IEventDao eventDao, ITicketDao ticketDao, CloudinaryService cloudinaryService) {
        this.eventDao = eventDao;
        this.ticketDao = ticketDao;
        this.cloudinaryService = cloudinaryService;
    }
    @Override
    @Transactional
    public EventResponse CreateEvent(EventRequest eventRequest, MultipartFile banner, MultipartFile seatMap) {
        try{
            String bannerPath = cloudinaryService.uploadFile(banner);
            String seatMapPath = cloudinaryService.uploadFile(seatMap);
            if(bannerPath.equals("") || seatMapPath.equals(""))
            {
                throw new Exception("Failed to upload file");
            }
            eventRequest.getEvent().setBannerPath(bannerPath);
            eventRequest.getEvent().setSeatMapPath(seatMapPath);
            int eventId = eventDao.CreateEvent(eventRequest.getEvent());
            if(eventId == -1)
            {
                throw new Exception("Failed to create event");
            }

            for(Ticket ticket : eventRequest.getTickets())
            {
                ticket.setEventId(eventId);
                if(!ticketDao.AddTicket(ticket))
                {
                    throw new Exception("Failed to create ticket");
                }
            }

            return EventResponse.builder().message("success").build();
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return EventResponse.builder().message(e.getMessage()).build();
        }
    }

    @Override
    public EventDto GetEventById(int id, String includeStr) {
        try{
            Event event = eventDao.GetEventById(id);
            EventDto eventDto = EventDto.builder().event(event).build();

            if(includeStr != null && includeStr.contains("tickets")){
                List<Ticket> tickets = ticketDao.GetTicketsOfEvent(event.getId());
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
            List<EventDto> eventDtos = new ArrayList<>();
            List<Event> events = eventDao.GetEvents(null, null);

            for(Event event : events)
            {
                EventDto eventDto = new EventDto();
                eventDto.setEvent(event);

                if(includeStr != null && includeStr.contains("tickets")){
                    List<Ticket> tickets = ticketDao.GetTicketsOfEvent(event.getId());
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
}
