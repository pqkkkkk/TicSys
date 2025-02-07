package com.example.ticsys.event.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.event.dto.request.EventRequest;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.model.Event;

public interface EventService {
    public EventResponse CreateEvent(EventRequest eventRequest, MultipartFile banner, MultipartFile seatMap);
    public Event GetEventById(int id);
    public List<Event> GetEvents(String category, String status);
}
