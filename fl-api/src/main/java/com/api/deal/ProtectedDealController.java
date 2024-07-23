package com.api.deal;

import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealResponse;
import com.common.utils.SuccessResponse;
import com.service.deal.ProtectedDealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/deal")
public class ProtectedDealController {
    private final ProtectedDealService protectedDealService;

    /**
     * 안전거래 생성 API
     * todo 사용자가 동시에 2번 요청을 보냈을경우 예외처리
     */
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    public ResponseEntity<?> saveDeal(@RequestBody ProtectedDealGeneratorRequest request) {
        protectedDealService.save(request);
        SuccessResponse response = new SuccessResponse(true, "안전거래 생성 완료", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 안전거래 조회 API
     */
    @PostMapping("/read")
    public ResponseEntity<?> findProtectedDeal(@RequestBody ProtectedDealFindRequest request) {
        ProtectedDealResponse protectedDealResponse = protectedDealService.findByDealInformation(request);
        SuccessResponse response = new SuccessResponse(true, "안전거래 조회 성공", protectedDealResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
        //getterId, providerId, dmId
    }

    /**
     * 자신의 안전거래 조회 API
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId){
        List<ProtectedDealResponse> allByUserId = protectedDealService.findAllByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, "내 안전거래 조회 성공", allByUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 입금 신청 API (GETTER 가 사용)
     */
    @PostMapping("/request/deposit/{dealId}")
    public ResponseEntity<?> requestDeposit(@PathVariable Long dealId) {
        protectedDealService.requestDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, "입금 신청 완료", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 입금 완료 API (ADMIN 이 사용)
     */
    @PatchMapping("/done/deposit/{dealId}")
    public ResponseEntity<?> doneDeposit(@PathVariable Long dealId) {
        protectedDealService.doneDeposit(dealId);
        SuccessResponse response = new SuccessResponse(true, "입금 완료", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 성사 API (GETTER 가 사용)
     */
    @PatchMapping("/done/{dealId}")
    public ResponseEntity<?> doneDeal(@PathVariable Long dealId){
        protectedDealService.finishDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, "거래 완료", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 거래 취소 API (GETTER 가 사용)
     */
    @PatchMapping("/cancel/{dealId}")
    public ResponseEntity<?> cancelDeal(@PathVariable Long dealId){
        protectedDealService.cancelDeal(dealId);
        SuccessResponse response = new SuccessResponse(true, "거래 취소", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
