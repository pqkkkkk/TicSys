package com.example.ticsys.event.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ticsys.event.dto.request.EventRequest;
import com.example.ticsys.event.dto.response.EventDetailResponse;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.model.Event;
import com.example.ticsys.event.model.Ticket;
import com.example.ticsys.event.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping
    public ResponseEntity<EventResponse> CreateEvent(@RequestParam("event") String eventJson,
                                        @RequestParam("tickets") String ticketsJson,
                                        @RequestParam("banner") MultipartFile banner,
                                        @RequestParam("seatMap") MultipartFile seatMap)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try{
            Event event = objectMapper.readValue(eventJson, Event.class);
            List<Ticket> tickets = objectMapper.readValue(ticketsJson, new TypeReference<List<Ticket>>() {});
            EventRequest eventRequest = EventRequest.builder().event(event).tickets(tickets).build();

            return ResponseEntity.ok(eventService.CreateEvent(eventRequest, banner, seatMap));
        }
        catch (JsonProcessingException e)
        {
            return ResponseEntity.ok(EventResponse.builder().message(e.getMessage()).build());
        }
    }
    @GetMapping
    public ResponseEntity<List<Event>> GetEvents(@RequestParam (required = false) String category,
                                         @RequestParam (required = false) String status)
    {
        return ResponseEntity.ok(eventService.GetEvents(category, status));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Event> GetEventById(@PathVariable int id)
    {
        return ResponseEntity.ok(eventService.GetEventById(id));
    }
    @GetMapping("/{id}/detail")
    public ResponseEntity<EventDetailResponse> GetEventDetail(@PathVariable int id)
    {
        return ResponseEntity.ok(eventService.GetEventDetail(id));
    }
}
