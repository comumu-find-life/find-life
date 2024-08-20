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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/admin/deals")
public class ProtectedDealController {

    private final ProtectedDealAdminService protectedDealAdminService;
    private final DirectMessageRoomService directMessageRoomService;

    /**
     * 모든 신청 거래된 안전거래 조회
     */
    @GetMapping("/before-deposit")
    public ResponseEntity<?> findAllBeforeDeposit() {
        List<ProtectedDealOverViewResponse> allBeforeDeposit = protectedDealAdminService.findAllBeforeDeposit();
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_ALL_BEFORE_DEPOSIT_SUCCESS, allBeforeDeposit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 신청 단일 조회 API
     */
    @GetMapping("/{dealId}")
    public ResponseEntity<?> findById(@PathVariable Long dealId) {
        ProtectedDealAdminResponse protectedDealAdminResponse = protectedDealAdminService.findById(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_BY_ID_SUCCESS, protectedDealAdminResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 확인 API
     */
    @PatchMapping("/{dealId}/confirm-deposit")
    public ResponseEntity<?> checkDeposit(@PathVariable Long dealId) {
        protectedDealAdminService.checkDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.CHECK_DEPOSIT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 완료 신청된 모든 안전거래 조회 API
     */
    @GetMapping("/submit-completed")
    public ResponseEntity<?> findAllSubmitDeal() {
        List<ProtectedDealOverViewResponse> allSubmitDeal = protectedDealAdminService.findAllSubmitDeal();
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_ALL_SUBMIT_DEAL_SUCCESS, allSubmitDeal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 완료 API (해당 API 를 호출하면 최종 거래 성사임을 확정 지음)
     */
    @PatchMapping("/{dealId}/complete")
    public ResponseEntity<?> completeDeal(@PathVariable Long dealId, @RequestBody DirectMessageRequest directMessageRequest) {
        directMessageRoomService.sendDealCompletionMessage(directMessageRequest);
        protectedDealAdminService.completeDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.COMPLETE_DEAL_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
