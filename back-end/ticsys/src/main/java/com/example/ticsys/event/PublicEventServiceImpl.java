package com.example.ticsys.event;

import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.ticsys.event.dao.event.IEventDao;
import com.example.ticsys.event.dao.ticket.ITicketDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {
    private final ITicketDao ticketDao;
    private final IEventDao eventDao;

    public PublicEventServiceImpl(ITicketDao ticketDao, IEventDao eventDao) {
        this.ticketDao = ticketDao;
        this.eventDao = eventDao;
    }
    @Override
    public Map<String, Object> GetTicketByRequiredFieldsList(List<String> requiredFields, int id) {
        try{
            return ticketDao.GetTicketByRequiredFieldsList(requiredFields, id);
        }
        catch (Exception e){
            return null;
        }
    }
    @Override
    public int UpdateTicketByRequiredFieldsList(Map<String, Object> newValues, int id) {
        try{
            int result =  ticketDao.UpdateTicketByRequiredFieldsList(newValues, id);
            return result;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }
    @Override
    public Map<String, Object> GetEventByRequiredFieldsList(List<String> requiredFields, int id) {
        try{
            return eventDao.GetEventByRequiredFieldsList(requiredFields, id);
        }
        catch (Exception e){
            return null;
        }
    }

}
