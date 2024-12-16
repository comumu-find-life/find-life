package com.api.point;

import org.springframework.ui.Model;
import com.common.point.request.PaymentRequest;
import com.common.utils.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.point.PaypalService;
import com.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.api.config.ApiUrlConstants.*;
import static com.api.point.SuccessPointMessages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PaypalService paypalService;
    private final PointService pointService;

    /**
     * 페이팔 결제 확인 후 포인트 충전 api
     */
    @PostMapping(CHARGE_POINT_BY_PAYPAL)
    public ResponseEntity<?> paymentSuccess(@RequestBody PaymentRequest request) throws JsonProcessingException {
        boolean isPayment = paypalService.verifyPayment(request);
        if(isPayment){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            pointService.chargePoint(email, request.getAmount());
            SuccessResponse response = new SuccessResponse(true, CHARGE_SUCCESS, null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        SuccessResponse response = new SuccessResponse(true, CHARGE_FAILED, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 출금 신청 api
     */
    @PostMapping(APPLY_WITH_DRAW_URL)
    public ResponseEntity<?> applyWithDraw(@RequestParam Integer price) throws IllegalAccessException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        pointService.applyWithDraw(email, price);
        SuccessResponse response = new SuccessResponse(true, APPLY_WITH_DRAW, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
