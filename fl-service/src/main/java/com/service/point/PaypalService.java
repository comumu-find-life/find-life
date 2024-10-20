package com.service.point;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private static final String PAYPAL_API_BASE = "https://api.sandbox.paypal.com";

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    private RestTemplate restTemplate = new RestTemplate();

    public String createPayment(String total, String currency) {
        // Step 1: Get Access Token
        String accessToken = getAccessToken();

        // Step 2: Create Payment
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> paymentData = new HashMap<>();
        paymentData.put("intent", "sale");

        Map<String, String> payer = new HashMap<>();
        payer.put("payment_method", "paypal");
        paymentData.put("payer", payer);

        Map<String, Object> amount = new HashMap<>();
        amount.put("total", total);
        amount.put("currency", currency);

        Map<String, Object> transaction = new HashMap<>();
        transaction.put("amount", amount);
        paymentData.put("transactions", new Map[]{transaction});

        Map<String, String> redirectUrls = new HashMap<>();
        redirectUrls.put("return_url", "https://example.com/success");
        redirectUrls.put("cancel_url", "https://example.com/cancel");
        paymentData.put("redirect_urls", redirectUrls);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentData, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                PAYPAL_API_BASE + "/v1/payments/payment", request, Map.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            Map<String, Object> responseBody = response.getBody();
            return responseBody.get("links").toString();
        }

        throw new RuntimeException("Failed to create PayPal payment");
    }


    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                PAYPAL_API_BASE + "/v1/oauth2/token", HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = response.getBody();
            return responseBody.get("access_token");
        }

        throw new RuntimeException("Failed to obtain PayPal access token");
    }
}
