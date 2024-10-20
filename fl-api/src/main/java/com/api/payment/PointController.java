package com.api.payment;

import com.common.utils.SuccessResponse;
import com.service.point.PaypalService;
import com.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.api.config.ApiUrlConstants.POINT_CHARGE_PAYPAL;
import static com.api.config.ApiUrlConstants.WITH_DRAW_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PaypalService paypalService;
    private final PointService pointService;

    /**
     * 페이팔 결제 요청 api
     */
    @PostMapping(POINT_CHARGE_PAYPAL)
    public ResponseEntity<String> createPayment(@RequestParam String total, @RequestParam String currency) {
        String approvalUrl = paypalService.createPayment(total, currency);
        return ResponseEntity.ok(approvalUrl);
    }

    /**
     * 출금 신청 api
     */
    @PostMapping(WITH_DRAW_URL)
    public ResponseEntity<?> withDraw(@RequestParam Integer amount) throws IllegalAccessException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer point = pointService.withdraw(email, amount);
        SuccessResponse response = new SuccessResponse(true, "출금 성공", point);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
