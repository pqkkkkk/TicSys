package com.example.ticsys.event;

import java.util.List;
import java.util.Map;

public interface PublicEventService {
    Map<String, Object> GetTicketByRequiredFieldsList(List<String> requiredFields, int id);
    int UpdateTicketByRequiredFieldsList(Map<String, Object> newValues, int id);
}
