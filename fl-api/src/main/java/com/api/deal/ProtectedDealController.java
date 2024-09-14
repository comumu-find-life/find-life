package com.api.deal;

import com.common.chat.request.DirectMessageRequest;
import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.MyProtectedDealResponse;
import com.common.deal.response.ProtectedDealByGetterResponse;
import com.common.deal.response.ProtectedDealByProviderResponse;
import com.common.utils.SuccessResponse;
import com.service.chat.DirectMessageRoomService;
import com.service.deal.ProtectedDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final DirectMessageRoomService directMessageRoomService;

    /**
     * 안전거래 생성 API
     */
    @PostMapping(DEALS_SAVE)
    public ResponseEntity<?> saveDeal(@RequestBody ProtectedDealGeneratorRequest request) {
        Long dealId = protectedDealService.save(request);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_CREATED, dealId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전거래 조회 API (By Getter)
     */
    @PostMapping(DEALS_GETTER_READ)
    public ResponseEntity<?> findProtectedDealByGetter(@RequestBody ProtectedDealFindRequest request) {
        List<ProtectedDealByGetterResponse> protectedDealResponse = protectedDealService.findByGetterDealInformation( request);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED, protectedDealResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전거래 조회 API (By Provider)
     */
    @PostMapping(DEALS_PROVIDER_READ)
    public ResponseEntity<?> findProtectedDealByProvider(@RequestBody ProtectedDealFindRequest request) {
        List<ProtectedDealByProviderResponse> protectedDealResponse = protectedDealService.findByProviderDealInformation( request);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED, protectedDealResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    /**
//     * id 로 안전거래 조회 API (By Getter)
//     */
//    @GetMapping(DEALS_READ_BY_ID_FROM_GETTER)
//    public ResponseEntity<?> findProtectedDealByIdFromGetter(@PathVariable Long dealId) {
//        ProtectedDealByGetterResponse protectedDealResponse = protectedDealService.findByIdFromGetter(dealId);
//        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED, protectedDealResponse);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    /**
//     * id 로 안전거래 조회 API (By Provide)
//     */
//    @GetMapping(DEALS_READ_BY_ID_FROM_PROVIDER)
//    public ResponseEntity<?> findProtectedDealByIdFromProvider(@PathVariable Long dealId) {
//        ProtectedDealByProviderResponse protectedDealResponse = protectedDealService.findByIdFromProvider(dealId);
//        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED, protectedDealResponse);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }



    /**
     * 내 모든 안전거래 조회 API (By Getter)
     */
    @GetMapping(DEALS_FIND_ALL_BY_USER_ID)
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId){
        List<MyProtectedDealResponse> allByUserId = protectedDealService.findAllByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_FETCHED_FOR_USER, allByUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 신청 API (By GETTER)
     */
    @PostMapping(DEALS_REQUEST_DEPOSIT)
    public ResponseEntity<?> requestDeposit(@PathVariable Long dealId) {
        protectedDealService.requestDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEPOSIT_REQUESTED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 완료 신청 API (By GETTER)
     */
    @PatchMapping(DEALS_REQUEST_COMPLETE_URL)
    public ResponseEntity<?> requestCompleteDeal(@PathVariable Long dealId) {
        protectedDealService.requestCompleteDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_REQUEST_COMPLETED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * 거래 완료 API (By Admin TODO 삭제 예정)
     */
    @PatchMapping(DEALS_COMPLETE)
    public ResponseEntity<?> completeDeal(@PathVariable Long dealId, @RequestBody DirectMessageRequest directMessageRequest){
        directMessageRoomService.sendDealCompletionMessage(directMessageRequest);
        protectedDealService.completeDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEAL_COMPLETED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 완료 API (TODO 삭제 예정)
     */
    @PatchMapping(DEALS_COMPLETE_DEPOSIT)
    public ResponseEntity<?> doneDeposit(@PathVariable Long dealId) {
        protectedDealService.completeDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, SuccessProtectedDealMessages.DEPOSIT_DONE, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * (1) 입금전에 취소한 상황
     * (2) 입금후에 취소한 상황
     */

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
