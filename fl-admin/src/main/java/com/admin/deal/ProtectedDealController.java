package com.admin.deal;

import com.common.chat.request.DirectMessageRequest;
import com.common.deal.response.ProtectedDealAdminResponse;
import com.common.deal.response.ProtectedDealOverViewResponse;
import com.common.utils.SuccessResponse;
import com.service.chat.DirectMessageRoomService;
import com.service.deal.ProtectedDealAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.admin.config.AdminApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ProtectedDealController {

    private final ProtectedDealAdminService protectedDealAdminService;
    private final DirectMessageRoomService directMessageRoomService;

    /**
     * 입금 신청된 안전거래 모두 조회 API
     */
    @GetMapping(DEALS_REQUEST_DEPOSIT)
    public ResponseEntity<?> findAllRequestDeposit() {
        List<ProtectedDealOverViewResponse> allBeforeDeposit = protectedDealAdminService.findAllRequestDeposit();
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_ALL_BEFORE_DEPOSIT_SUCCESS, allBeforeDeposit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전거래 단일 조회 API
     */
    @GetMapping(DEAL_BY_ID)
    public ResponseEntity<?> findById(@PathVariable Long dealId) {
        ProtectedDealAdminResponse protectedDealAdminResponse = protectedDealAdminService.findById(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_BY_ID_SUCCESS, protectedDealAdminResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 입금 완료 API
     * 해당 API 요청은 관리자(Admin) 이 입금이 완료 됨을 확인 후 사용한다.
     */
    @PatchMapping(DEAL_CONFIRM_DEPOSIT)
    public ResponseEntity<?> checkDeposit(@PathVariable Long dealId) {
        protectedDealAdminService.checkDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.CHECK_DEPOSIT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 신청 신청 취소 API
     * 해당 API 는 관리자(Admin) 이 입금이 완료되지 않은 뒤 사용한다
     */
    @PatchMapping(DEAL_CANCEL_DEPOSIT)
    public ResponseEntity<?> cancelDeposit(@PathVariable Long dealId){
        protectedDealAdminService.cancelDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.CANCEL_DEPOSIT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 완료 신청 안전 거래 모두 조회 API
     */
    @GetMapping(DEAL_SUBMIT_COMPLETED)
    public ResponseEntity<?> findAllSubmitDeal() {
        List<ProtectedDealOverViewResponse> allSubmitDeal = protectedDealAdminService.findAllSubmitDeal();
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_ALL_SUBMIT_DEAL_SUCCESS, allSubmitDeal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 완료 API
     */
    @PatchMapping(DEAL_COMPLETE)
    public ResponseEntity<?> completeDeal(@PathVariable Long dealId, @RequestBody DirectMessageRequest directMessageRequest) {
        directMessageRoomService.sendDealCompletionMessage(directMessageRequest);
        protectedDealAdminService.completeDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.COMPLETE_DEAL_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}