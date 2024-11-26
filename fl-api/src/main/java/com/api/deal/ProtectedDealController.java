package com.api.deal;

import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.MyProtectedDealResponse;
import com.common.deal.response.ProtectedDealGeneratorResponse;
import com.common.deal.response.ProtectedDealResponse;
import com.common.utils.SuccessResponse;
import com.service.deal.ProtectedDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.config.ApiUrlConstants.*;

/**
 * 클라이언트가 사용할 안전거래 API
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProtectedDealController {

    private final ProtectedDealService protectedDealService;

    /**
     * 안전 거래 단일 조회 API
     */
    @PostMapping(DEALS_GETTER_READ)
    public ResponseEntity<?> findProtectedDealByGetter(@RequestBody ProtectedDealFindRequest request) {
        List<ProtectedDealResponse> protectedDealResponse = protectedDealService.findProtectedDeal( request);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED, protectedDealResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 내 모든 안전 거래 조회 API (By Getter)
     */
    @GetMapping(DEALS_FIND_ALL_BY_USER_ID)
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId){
        List<MyProtectedDealResponse> allByUserId = protectedDealService.findAllByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED_FOR_USER, allByUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전 거래 생성 API (By Provider)
     */
    @PostMapping(DEALS_SAVE)
    public ResponseEntity<?> saveProtectedDeal(@RequestBody ProtectedDealGeneratorRequest request) throws Exception {
        ProtectedDealGeneratorResponse protectedDeal = protectedDealService.saveProtectedDeal(request);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_CREATED, protectedDeal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 안전 거래 생성 수락 API (By GETTER)
     */
    @PostMapping(DEALS_ACCEPT_REQUEST)
    public ResponseEntity<?> acceptDeal(@PathVariable Long dealId) throws Exception {
        String secretKey = protectedDealService.acceptProtectedDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEPOSIT_REQUESTED, secretKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전 거래 완료 API (By GETTER)
     */
    @PatchMapping(DEALS_REQUEST_COMPLETE_URL)
    public ResponseEntity<?> requestCompleteDeal(@PathVariable Long dealId, @RequestBody String secretKey) throws Exception {
        protectedDealService.requestCompleteDeal(dealId, secretKey);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_REQUEST_COMPLETED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전 거래 생성 전 취소 API (By GETTER)
     */
    @PatchMapping(DEALS_CANCEL_BEFORE_URL)
    public ResponseEntity<?> cancelBeforeDeal(@PathVariable Long dealId) {
        protectedDealService.cancelBeforeDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEPOSIT_CANCELLED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전 거래 생성 후 취소 API (By GETTER)
     */
    @PatchMapping(DEALS_CANCEL_AFTER)
    public ResponseEntity<?> cancelAfterDeal(@PathVariable Long dealId){
        protectedDealService.cancelAfterDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_CANCELLED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
