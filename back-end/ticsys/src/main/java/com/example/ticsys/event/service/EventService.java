package com.example.ticsys.event.service;


import java.util.Map;


import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.event.dto.EventDto;
import com.example.ticsys.event.dto.request.EventRequest;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.dto.response.GetEventsResponse;

public interface EventService {
    public EventResponse CreateEvent(EventRequest eventRequest, MultipartFile banner, MultipartFile seatMap);
    public EventDto GetEventById(int id, String includeStr);
    public GetEventsResponse GetEvents(String includeStr, Map<String, Object> filterMap);
}
