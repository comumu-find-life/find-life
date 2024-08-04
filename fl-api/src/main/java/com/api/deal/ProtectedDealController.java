package com.api.deal;

import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealResponse;
import com.common.deal.response.ProtectedDealResponseV2;
import com.common.utils.SuccessResponse;
import com.service.deal.ProtectedDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.config.ApiUrlConstants.*;

/**
 * 클라이언트가 사용할 안전거래 API 다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class ProtectedDealController {
    private final ProtectedDealService protectedDealService;

    /**
     * 안전거래 생성 API
     */
    @PostMapping(DEALS_SAVE)
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    public ResponseEntity<?> saveDeal(@RequestBody ProtectedDealGeneratorRequest request) {
        protectedDealService.save(request);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_CREATED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전거래 조회 API
     */
    @PostMapping(DEALS_READ)
    public ResponseEntity<?> findProtectedDeal(@RequestBody ProtectedDealFindRequest request) {
        ProtectedDealResponse protectedDealResponse = protectedDealService.findByDealInformation(request);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED, protectedDealResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 내 안전거래 조회 API
     */
    @GetMapping(DEALS_FIND_ALL_BY_USER_ID)
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId){
        List<ProtectedDealResponseV2> allByUserId = protectedDealService.findAllByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED_FOR_USER, allByUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 신청 API
     */
    @PostMapping(DEALS_REQUEST_DEPOSIT)
    public ResponseEntity<?> requestDeposit(@PathVariable Long dealId) {
        protectedDealService.requestDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEPOSIT_REQUESTED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 완료 API
     */
    @PatchMapping(DEALS_DONE_DEPOSIT)
    public ResponseEntity<?> doneDeposit(@PathVariable Long dealId) {
        protectedDealService.doneDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEPOSIT_DONE, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 완료 API
     */
    @PatchMapping(DEALS_DONE)
    public ResponseEntity<?> doneDeal(@PathVariable Long dealId){
        protectedDealService.finishDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_COMPLETED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 취소 API
     */
    @PatchMapping(DEALS_CANCEL)
    public ResponseEntity<?> cancelDeal(@PathVariable Long dealId){
        protectedDealService.cancelDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_CANCELLED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
