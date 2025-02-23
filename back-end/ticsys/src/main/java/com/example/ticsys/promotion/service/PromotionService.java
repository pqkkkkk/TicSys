package com.example.ticsys.promotion.service;

import java.util.List;

import com.example.ticsys.promotion.model.Promotion;

public interface PromotionService {
    int CreatePromotion(Promotion promotion);
    boolean UpdatePromotion(Promotion promotion);
    List<Promotion> GetPromotions(int eventId);
}
