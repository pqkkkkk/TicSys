package com.example.ticsys.event.dao.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ticsys.event.model.Event;
import com.example.ticsys.event.rowmapper.EventRowMapper;

@Repository
public class EventSqlDao implements IEventDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public EventSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean CreateEvent(Event event) {
        String sql = """
            INSERT INTO event (name,organizerId, location, description, bannerPath, seatMapPath, status, category, date, time)
            VALUES (:name,:organizerId, :location, :description, :bannerPath, :seatMapPath, :status, :category, :date, :time)
            """;
        Map<String, Object> params = new HashMap<>();
        params.put("name", event.getName());
        params.put("organizerId", event.getOrganizerId());
        params.put("location", event.getLocation());
        params.put("description", event.getDescription());
        params.put("bannerPath", event.getBannerPath());
        params.put("seatMapPath", event.getSeatMapPath());
        params.put("status", event.getStatus());
        params.put("category", event.getCategory());
        params.put("date", event.getDate());
        params.put("time", event.getTime());

        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public Event GetEventById(int id) {
        String sql = "SELECT * FROM event WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(sql, params, new EventRowMapper());
    }
    @Override
    public List<Event> GetEvents(String category, String status) {
        String sql = "SELECT * FROM event WHERE 1=1 ";
        Map<String, Object> params = new HashMap<>();
        if (category != null) {
            sql += "AND category = :category ";
            params.put("category", category);
        }
        if (status != null) {
            sql += "AND status = :status ";
            params.put("status", status);
        }
        return jdbcTemplate.query(sql, params, new EventRowMapper());
    }

}
