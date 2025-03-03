package com.example.ticsys.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticsys.promotion.dto.PromotionInfoOfEventResponse;
import com.example.ticsys.promotion.model.Promotion;
import com.example.ticsys.promotion.service.PromotionService;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping
    public ResponseEntity<Integer> CreatePromotion(@RequestBody Promotion promotion) {
        int result = promotionService.CreatePromotion(promotion);

        if(result > 0){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> UpdatePromotion(@RequestBody Promotion promotion) {
        boolean result = promotionService.UpdatePromotion(promotion);

        if(result){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Promotion> GetPromotionById(@PathVariable int id) {
        Promotion result = promotionService.GetPromotionById(id);

        if(result != null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping
    public ResponseEntity<List<Promotion>> GetPromotions(@RequestParam(required = false) Integer eventId,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String type) {

        if(eventId == null){
            eventId = -1;
        }

        List<Promotion> result = promotionService.GetPromotions(eventId.intValue(), status, type);

        if(result != null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/reductionInfo")
    public ResponseEntity<PromotionInfoOfEventResponse> GetReductionInfoOfPromotionsOfEvent(@RequestParam int eventId, @RequestParam int currentPrice) {
        PromotionInfoOfEventResponse result = promotionService.GetReductionInfoOfPromotionsOfEvent(eventId, currentPrice);

        if(result != null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
}
