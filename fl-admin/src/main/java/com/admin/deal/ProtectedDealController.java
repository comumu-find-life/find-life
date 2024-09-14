package com.admin.deal;

import com.admin.config.AdminApiUrls;
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
@RequestMapping()  // 상수로 URL 지정
public class ProtectedDealController {

    private final ProtectedDealAdminService protectedDealAdminService;
    private final DirectMessageRoomService directMessageRoomService;

    @GetMapping(DEALS_BEFORE_DEPOSIT)
    public ResponseEntity<?> findAllBeforeDeposit() {
        List<ProtectedDealOverViewResponse> allBeforeDeposit = protectedDealAdminService.findAllBeforeDeposit();
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_ALL_BEFORE_DEPOSIT_SUCCESS, allBeforeDeposit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DEAL_BY_ID)
    public ResponseEntity<?> findById(@PathVariable Long dealId) {
        ProtectedDealAdminResponse protectedDealAdminResponse = protectedDealAdminService.findById(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_BY_ID_SUCCESS, protectedDealAdminResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(DEAL_CONFIRM_DEPOSIT)
    public ResponseEntity<?> checkDeposit(@PathVariable Long dealId) {
        protectedDealAdminService.checkDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.CHECK_DEPOSIT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DEAL_SUBMIT_COMPLETED)
    public ResponseEntity<?> findAllSubmitDeal() {
        List<ProtectedDealOverViewResponse> allSubmitDeal = protectedDealAdminService.findAllSubmitDeal();
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.FIND_ALL_SUBMIT_DEAL_SUCCESS, allSubmitDeal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(DEAL_COMPLETE)
    public ResponseEntity<?> completeDeal(@PathVariable Long dealId, @RequestBody DirectMessageRequest directMessageRequest) {
        directMessageRoomService.sendDealCompletionMessage(directMessageRequest);
        protectedDealAdminService.completeDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, ProtectedDealSuccessMessages.COMPLETE_DEAL_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}