package com.example.ticsys.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticsys.promotion.dao.IPromotionDao;
import com.example.ticsys.promotion.model.Promotion;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PromotionServiceImpl implements PromotionService {
    private final IPromotionDao promotionDao;

    @Autowired
    public PromotionServiceImpl(IPromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }
    @Override
    public int CreatePromotion(Promotion promotion) {
        try{
            return promotionDao.CreatePromotion(promotion);
        }
        catch(Exception e){
            log.error("Error in CreatePromotion of PromotionService", e);
            return -1;
        }
    }

    @Override
    public boolean UpdatePromotion(Promotion promotion) {
        try{
            return promotionDao.UpdatePromotion(promotion);
        }
        catch(Exception e){
            log.error("Error in UpdatePromotion of PromotionService", e);
            return false;
        }
    }

    @Override
    public List<Promotion> GetPromotions(int eventId) {
        try{
            return promotionDao.GetPromotions(eventId);
        }
        catch(Exception e){
            log.error("Error in GetPromotions of PromotionService", e);
            return null;
        }
    }

}
