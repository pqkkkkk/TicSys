package com.example.ticsys.event.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.event.dao.event.IEventDao;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.model.Event;
import com.example.ticsys.media.CloudinaryService;

@Service
public class EventService {
    private final IEventDao eventDao;
    private final CloudinaryService cloudinaryService;
    public EventService(IEventDao eventDao, CloudinaryService cloudinaryService) {
        this.eventDao = eventDao;
        this.cloudinaryService = cloudinaryService;
    }
    public EventResponse CreateEvent(Event event, MultipartFile banner, MultipartFile seatMap)
    {
        String bannerPath = cloudinaryService.uploadFile(banner);
        String seatMapPath = cloudinaryService.uploadFile(seatMap);
        if(!bannerPath.equals("") && !seatMapPath.equals(""))
        {
            event.setBannerPath(bannerPath);
            event.setSeatMapPath(seatMapPath);
            if(eventDao.CreateEvent(event))
            {
                return EventResponse.builder().message("success").build();
            }
            else
            {
                return EventResponse.builder().message("fail").build();
            }
        }
        else
        {
            return EventResponse.builder().message("file error").build();
        }
    }
    public Event GetEventById(int id)
    {
        return eventDao.GetEventById(id);
    }
    public List<Event> GetEvents(String category, String status)
    {
        return eventDao.GetEvents(category, status);
    }
}
