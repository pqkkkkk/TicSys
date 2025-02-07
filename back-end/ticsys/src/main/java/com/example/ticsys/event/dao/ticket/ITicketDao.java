package com.example.ticsys.event.dao.ticket;

import java.util.List;

import com.example.ticsys.event.model.Ticket;

public interface ITicketDao {
    boolean AddTicket(Ticket ticket);
    List<Ticket> GetTicketsOfEvent(int eventId);
}
