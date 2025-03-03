package com.example.ticsys.promotion.service;

import java.util.List;

import com.example.ticsys.promotion.dto.PromotionInfoOfEventResponse;
import com.example.ticsys.promotion.model.Promotion;
import com.example.ticsys.promotion.model.VoucherOfUser;

public interface PromotionService {
    int CreatePromotion(Promotion promotion);
    boolean UpdatePromotion(Promotion promotion);
    Promotion GetPromotionById(int id);
    List<Promotion> GetPromotions(int eventId, String status, String type);
    PromotionInfoOfEventResponse GetReductionInfoOfPromotionsOfEvent(int eventId, int currentPrice);

    List<VoucherOfUser> GetVoucherOfUsers(String userId, int promotionId, String status);
}
