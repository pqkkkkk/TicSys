package com.example.ticsys.promotion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticsys.promotion.dao.promotion.IPromotionDao;
import com.example.ticsys.promotion.dao.voucherOfUser.IVoucherOfUserDao;
import com.example.ticsys.promotion.dto.PromotionInfo;
import com.example.ticsys.promotion.dto.PromotionInfoOfEventResponse;
import com.example.ticsys.promotion.model.Promotion;
import com.example.ticsys.promotion.model.VoucherOfUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PromotionServiceImpl implements PromotionService {
    private final IPromotionDao promotionDao;
    private final IVoucherOfUserDao voucherOfUserDao;

    @Autowired
    public PromotionServiceImpl(IPromotionDao promotionDao, IVoucherOfUserDao voucherOfUserDao) {
        this.promotionDao = promotionDao;
        this.voucherOfUserDao = voucherOfUserDao;
    }
    @Override
    public int CreatePromotion(Promotion promotion) {
        try{
            promotion.setStatus("active");
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
    public List<Promotion> GetPromotions(int eventId, String status, String type) {
        try{
            return promotionDao.GetPromotions(eventId, status, type);
        }
        catch(Exception e){
            log.error("Error in GetPromotions of PromotionService", e);
            return null;
        }
    }
    @Override
    public PromotionInfoOfEventResponse GetReductionInfoOfPromotionsOfEvent(int eventId, int currentPrice) {
        try{
            List<PromotionInfo> promotionInfos = new ArrayList<>();

            List<Promotion> promotions = GetPromotions(eventId, "active", null);

            for(Promotion promotion : promotions){
                String promotionType = promotion.getType();
                PromotionInfo promotionInfo = new PromotionInfo();
                promotionInfo.setPromotion(promotion);

                switch (promotionType) {
                    case "Voucher Gift":
                        if(currentPrice < promotion.getMinPriceToReach()){
                            promotionInfo.setReduction(0);
                        }
                        else{
                        promotionInfo.setReduction(promotion.getVoucherValue());
                        }
                        break;
                    case "Flash Sale":
                        int reduction = promotion.getPromoPercent() * currentPrice / 100;
                        promotionInfo.setReduction(reduction);
                        break;
                    default:
                        break;
                }
                promotionInfos.add(promotionInfo);
            }
            
            PromotionInfoOfEventResponse response =  PromotionInfoOfEventResponse.builder()
                    .message("success")
                    .promotionInfos(promotionInfos)
                    .build();
            return response;

        }
        catch(Exception e){
            log.error("Error in GetInfoAboutPromotionsOfEvent of PromotionService", e);
            return null;
        }
    }
    @Override
    public Promotion GetPromotionById(int id) {
        try{
            return promotionDao.GetPromotionById(id);
        }
        catch(Exception e){
            log.error("Error in GetPromotionById of PromotionService", e);
            return null;
        }
    }
    @Override
    public List<VoucherOfUser> GetVoucherOfUsers(String userId, int promotionId, String status) {
        try{
            return voucherOfUserDao.GetVoucherOfUsers(userId, promotionId, status);
        }
        catch(Exception e){
            log.error("Error in GetVoucherOfUsers of PromotionService", e);
            return null;
        }
    }

}
