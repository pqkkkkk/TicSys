package com.example.ticsys.event.dao.event;

import java.util.List;

import com.example.ticsys.event.model.Event;

public interface IEventDao {
    public int CreateEvent(Event event);
    public Event GetEventById(int id);
    public List<Event> GetEvents(String category, String status);
}
