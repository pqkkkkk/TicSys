package com.example.ticsys.promotion.controller;


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

import com.example.ticsys.promotion.dto.PromotionsResponse;
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
    public ResponseEntity<Integer> UpdatePromotion(@RequestBody Promotion promotion) {
        int result = promotionService.UpdatePromotion(promotion);

        if(result != 0){
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
    public ResponseEntity<PromotionsResponse> GetPromotions(@RequestParam(required = false) Integer eventId,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String type,
                                                         @RequestParam(required = false) String includeStr) {

        if(eventId == null){
            eventId = -1;
        }

        PromotionsResponse result = promotionService.GetPromotions(eventId.intValue(), status, type);

        if(result.getMessage().equals("success")){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/reductionInfo")
    public ResponseEntity<PromotionsResponse> GetReductionInfoOfPromotionsOfEvent(@RequestParam int eventId, @RequestParam int currentPrice) {
        PromotionsResponse result = promotionService.GetReductionInfoOfPromotionsOfEvent(eventId, currentPrice);

        if(result != null){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
    @GetMapping("/orderCount")
    public ResponseEntity<PromotionsResponse> GetPromotionsWithOrderCount(@RequestParam(required = false) Integer eventId,
                                                         @RequestParam(required = false) String status,
                                                         @RequestParam(required = false) String type) {

        if(eventId == null){
            eventId = -1;
        }

        PromotionsResponse result = promotionService.GetPromotionsWithOrderCount(eventId.intValue(), status, type);

        if(result.getMessage().equals("success")){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
