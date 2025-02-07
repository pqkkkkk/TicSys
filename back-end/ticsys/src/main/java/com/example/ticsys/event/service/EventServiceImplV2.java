package com.example.ticsys.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.event.dao.event.IEventDao;
import com.example.ticsys.event.dao.ticket.ITicketDao;
import com.example.ticsys.event.dto.request.EventRequest;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.model.Event;
import com.example.ticsys.event.model.Ticket;
import com.example.ticsys.media.CloudinaryService;

@Primary
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
            if(bannerPath.equals("") && seatMapPath.equals(""))
            {
                throw new Exception("Failed to upload file");
            }
            if(!eventDao.CreateEvent(eventRequest.getEvent()))
            {
                throw new Exception("Failed to create event");
            }
            for(Ticket ticket : eventRequest.getTickets())
            {
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
    public Event GetEventById(int id) {
        return eventDao.GetEventById(id);
    }

    @Override
    public List<Event> GetEvents(String category, String status) {
        return eventDao.GetEvents(category, status);
    }

}
