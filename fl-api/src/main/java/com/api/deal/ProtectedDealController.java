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



}
