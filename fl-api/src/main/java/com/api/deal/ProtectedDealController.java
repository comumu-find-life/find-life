package com.api.deal;

import com.service.deal.ProtectedDealService;
import com.service.deal.dto.CreateProtectedDealDto;
import com.service.deal.dto.ProtectedDealResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class ProtectedDealController {
    private final ProtectedDealService protectedDealService;

    @PostMapping("/deal")
    @PreAuthorize("hasRole('ROLE_GETTER')")
    public ResponseEntity<Long> saveDeal(@RequestBody CreateProtectedDealDto dto){
        return ResponseEntity.ok(protectedDealService.createDeal(dto));
    }

    @GetMapping("/deal")
    public ResponseEntity<ProtectedDealResponse> agreeDealByGetter(Long dealId){
        return ResponseEntity.ok(protectedDealService.findById(dealId));
    }

    //안전거래 취소 메서드
    @PostMapping("/deal/cancel")
    public ResponseEntity<String> cancelDeal(ProtectedDealResponse dealResponse){
        protectedDealService.cancelProtectedDeal(dealResponse);
        return ResponseEntity.ok("deal cancel");
    }

    //안전거래 성사 메서드
    @PostMapping("/deal/finish")
    public ResponseEntity<String> finishDeal(ProtectedDealResponse dealResponse){
        protectedDealService.finishProtectedDeal(dealResponse);
        return ResponseEntity.ok("deal finish");
    }
}
