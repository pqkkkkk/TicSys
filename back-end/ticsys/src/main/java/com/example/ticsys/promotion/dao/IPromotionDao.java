package com.example.ticsys.promotion.dao;

import java.util.List;

import com.example.ticsys.promotion.model.Promotion;

public interface IPromotionDao {
    public int CreatePromotion(Promotion promotion);
    public boolean UpdatePromotion(Promotion promotion);
    public List<Promotion> GetPromotions(int eventId);
}
