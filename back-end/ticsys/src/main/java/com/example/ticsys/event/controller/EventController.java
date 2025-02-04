package com.example.ticsys.event.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.ticsys.event.dto.response.EventResponse;
import com.example.ticsys.event.model.Event;
import com.example.ticsys.event.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }
    @PostMapping
    public ResponseEntity<EventResponse> CreateEvent(@ModelAttribute Event event,
                                        @RequestParam("banner") MultipartFile banner,
                                        @RequestParam("seatMap") MultipartFile seatMap)
    {
        return ResponseEntity.ok(eventService.CreateEvent(event, banner, seatMap));
    }
    @GetMapping
    public ResponseEntity<List<Event>> GetEvents(@RequestParam (required = false) String category,
                                         @RequestParam (required = false) String status)
    {
        return ResponseEntity.ok(eventService.GetEvents(category, status));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Event> GetEventById(@RequestParam int id)
    {
        return ResponseEntity.ok(eventService.GetEventById(id));
    }
}
