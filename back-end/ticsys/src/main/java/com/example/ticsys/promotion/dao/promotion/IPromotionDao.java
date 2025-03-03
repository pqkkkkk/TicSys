package com.example.ticsys.promotion.dao.promotion;

import java.util.List;

import com.example.ticsys.promotion.model.Promotion;

public interface IPromotionDao {
    public int CreatePromotion(Promotion promotion);
    public boolean UpdatePromotion(Promotion promotion);
    public List<Promotion> GetPromotions(int eventId, String status, String type);
    public Promotion GetPromotionById(int promotionId);
}
