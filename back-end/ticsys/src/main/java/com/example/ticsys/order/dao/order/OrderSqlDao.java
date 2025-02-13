package com.example.ticsys.order.dao.order;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.ticsys.order.model.Order;
import com.example.ticsys.order.rowmapper.OrderRowMapper;

@Repository
public class OrderSqlDao implements IOrderDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int CreateOrder(Order order) {
        String sql = """ 
            INSERT INTO [order] (price, createdBy, eventId, dateCreatedAt, timeCreatedAt, status, promotionId)
            VALUES (:price, :createdBy, :eventId, :dateCreatedAt, :timeCreatedAt, :status, :promotionId)
        """;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("price", order.getPrice());
        paramMap.put("createdBy", order.getCreatedBy());
        paramMap.put("eventId", order.getEventId());
        paramMap.put("dateCreatedAt", order.getDateCreatedAt());
        paramMap.put("timeCreatedAt", order.getTimeCreatedAt());
        paramMap.put("status", order.getStatus());
        paramMap.put("promotionId", order.getPromotionId() == -1 ? null : order.getPromotionId());

        SqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updateCount = jdbcTemplate.update(sql, parameterSource, keyHolder, new String[] {"id"});

        if(updateCount > 0){
            Number key = keyHolder.getKey();
            if (key != null) {
                int eventId = key.intValue();
                return eventId;
            }
        }
        return -1;
    }

    @Override
    public Order GetOrderById(int id) {
        String sql ="""
            SELECT * FROM [order] WHERE id = :id
        """;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);

        return jdbcTemplate.queryForObject(sql, paramMap, new OrderRowMapper());
    }

    @Override
    public List<Order> GetOrders(String userId, LocalDate dateCreated,
                     Time timeCreated, String status, int eventId) {
        String sql = "SELECT * FROM [order] WHERE 1=1 ";
        Map<String, Object> paramMap = new HashMap<>();

        if(userId != null){
            sql += "AND createdBy = :userId ";
            paramMap.put("userId", userId);
        }
        if(dateCreated != null){
            sql += "AND dateCreatedAt = :dateCreated ";
            paramMap.put("dateCreated", dateCreated);
        }
        if(timeCreated != null){
            sql += "AND timeCreatedAt = :timeCreated ";
            paramMap.put("timeCreated", timeCreated);
        }
        if(status != null){
            sql += "AND status = :status ";
            paramMap.put("status", status);
        }
        
        return jdbcTemplate.query(sql, paramMap, new OrderRowMapper());
    }
    @Override
    public boolean UpdateOrderStatus(int id, String status) {
        String sql = """
            UPDATE [order] SET status = :status WHERE id = :id
        """;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", status);
        paramMap.put("id", id);

        return jdbcTemplate.update(sql, paramMap) > 0;
    }

}
