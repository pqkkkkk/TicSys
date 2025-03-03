package com.example.ticsys.promotion.dao.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.ticsys.promotion.model.Promotion;
import com.example.ticsys.promotion.rowmapper.PromotionRowMapper;

@Repository
public class PromotionSqlDao implements IPromotionDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public PromotionSqlDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int CreatePromotion(Promotion promotion) {
        String sql = """
                insert into [promotion] (eventId, minPriceToReach, promoPercent, voucherValue, status, type, startDate, endDate)
                values (:eventId, :minPriceToReach, :promoPercent, :voucherValue, :status, :type, :startDate, :endDate)
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("eventId", promotion.getEventId());
        params.put("minPriceToReach", promotion.getMinPriceToReach());
        params.put("promoPercent", promotion.getPromoPercent());
        params.put("voucherValue", promotion.getVoucherValue());
        params.put("status", promotion.getStatus());
        params.put("type", promotion.getType());
        params.put("startDate", promotion.getStartDate());
        params.put("endDate", promotion.getEndDate());
        
        SqlParameterSource parameterSource = new MapSqlParameterSource(params);
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
    public boolean UpdatePromotion(Promotion promotion) {
        String sql = """
                update [promotion] set 
                minPriceToReach = :minPriceToReach, promoPercent = :promoPercent,
                voucherValue = :voucherValue, status = :status, type = :type, startDate = :startDate, endDate = :endDate
                where id = :id
                """;
        
        Map<String, Object> params = new HashMap<>();
        params.put("id", promotion.getId());
        params.put("minPriceToReach", promotion.getMinPriceToReach());
        params.put("promoPercent", promotion.getPromoPercent());
        params.put("voucherValue", promotion.getVoucherValue());
        params.put("status", promotion.getStatus());
        params.put("type", promotion.getType());
        params.put("startDate", promotion.getStartDate());
        params.put("endDate", promotion.getEndDate());

        return jdbcTemplate.update(sql, params) > 0;
    }

    @Override
    public List<Promotion> GetPromotions(int eventId, String status, String type) {
        String sql = "SELECT * FROM [promotion] WHERE 1=1 ";
        Map<String, Object> paramMap = new HashMap<>();

        if(eventId != -1){
            sql += "AND eventId = :eventId ";
            paramMap.put("eventId", eventId);
        }
        if(status != null){
            sql += "AND status = :status ";
            paramMap.put("status", status);
        }
        if(type != null){
            sql += "AND type = :type ";
            paramMap.put("type", type);
        }
        return jdbcTemplate.query(sql, paramMap, new PromotionRowMapper());
    }

    @Override
    public Promotion GetPromotionById(int promotionId) {
        String sql = "SELECT * FROM [promotion] WHERE id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", promotionId);
        return jdbcTemplate.queryForObject(sql, paramMap, new PromotionRowMapper());
    }

}
