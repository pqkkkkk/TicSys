package com.example.ticsys.event.dao.event.query;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.ticsys.event.dto.TimelyEventDataDto;
import com.example.ticsys.event.rowmapper.TimelyEventDataRowMapper;

@Repository
public class EventQuerySqlDao implements IEventQueryDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EventQuerySqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<TimelyEventDataDto> GetTimelyEventRevenueByEventId(Integer eventId, String startDate, String endDate) {
        StringBuilder sql = new StringBuilder("""
                WITH date_series AS (
                SELECT DATEADD(DAY, number, :startDate) AS ngay
                FROM master.dbo.spt_values
                WHERE type = 'P'
                AND DATEADD(DAY, number, :startDate) <= :endDate
                )
                SELECT 
                    ds.ngay as label,
                    ISNULL(SUM(o.price), 0) AS value
                FROM 
                    date_series ds
                LEFT JOIN 
                    [Order] o ON CAST(o.DateCreatedAt AS DATE) = ds.ngay and o.status = 'PAID' and o.eventId = :eventId
                GROUP BY 
                    ds.ngay
                ORDER BY 
                    ds.ngay ASC;
                """);
        Map<String, Object> params = Map.of("startDate", startDate, "endDate", endDate, "eventId", eventId);
        return jdbcTemplate.query(sql.toString(), params, new TimelyEventDataRowMapper());
        
    }
    @Override
    public List<TimelyEventDataDto> GetTimelyEventTicketCountByEventId(Integer eventId, String startDate,
            String endDate) {
        StringBuilder sql = new StringBuilder("""
            WITH date_series AS (
            SELECT DATEADD(DAY, number, :startDate) AS ngay
            FROM master.dbo.spt_values
            WHERE type = 'P'
            AND DATEADD(DAY, number, :startDate) <= :endDate
            )
            SELECT 
                ds.ngay as label,
                ISNULL(SUM(too.quantity), 0) AS value
            FROM 
                date_series ds
            LEFT JOIN 
                [Order] o ON CAST(o.DateCreatedAt AS DATE) = ds.ngay and o.eventId = :eventId
            LEFT JOIN [TicketOfOrder] too on o.ID = too.orderId
            GROUP BY 
                ds.ngay
            ORDER BY 
                ds.ngay ASC;
                """);
            
        Map<String, Object> params = Map.of("startDate", startDate, "endDate", endDate, "eventId", eventId);
        return jdbcTemplate.query(sql.toString(), params, new TimelyEventDataRowMapper());
    }
    @Override
    public String GetUsernameOfEventOwner(Integer eventId) {
        String sql = "SELECT e.organizerId FROM [event] e WHERE e.ID = :eventId";
        Map<String, Object> params = Map.of("eventId", eventId);
        return jdbcTemplate.queryForObject(sql, params, String.class);
    }
    

}
