package com.example.ticsys.event.dao.event.query;

import java.util.List;

import com.example.ticsys.event.dto.TimelyEventDataDto;

public interface IEventQueryDao {
    List<TimelyEventDataDto> GetTimelyEventRevenueByEventId(Integer eventId, String startDate, String endDate);
    List<TimelyEventDataDto> GetTimelyEventTicketCountByEventId(Integer eventId, String startDate, String endDate);
}
