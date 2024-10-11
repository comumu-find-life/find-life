package com.api.payment;

import com.service.paypal.PaypalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paypal")
public class PaymentController {

    private final PaypalService paypalService;

    /**
     * 페이팔 결제 요청 api
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam String total, @RequestParam String currency) {
        String approvalUrl = paypalService.createPayment(total, currency);
        return ResponseEntity.ok(approvalUrl);
    }

}
