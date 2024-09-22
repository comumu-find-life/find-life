package com.api.email;

import com.api.user.SuccessUserMessages;
import com.common.utils.SuccessResponse;
import com.redis.user.service.EmailRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.api.config.ApiUrlConstants.SEND_EMAIL_URL;
import static com.api.config.ApiUrlConstants.VERIFICATION_EMAIL_CODE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class EmailController {

    private final EmailRedisService emailRedisService;
    /**
     * email 인증 번호 전송 API
     */
    @PostMapping(SEND_EMAIL_URL)
    public ResponseEntity<?> sendCheckCode(@PathVariable String email) {
        emailRedisService.sendVerificationCode(email);
        SuccessResponse response = new SuccessResponse(true, SuccessEmailMessage.VERIFICATION_CODE_SENT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * email 인증 API
     */
    @PostMapping(VERIFICATION_EMAIL_CODE_URL)
    public ResponseEntity<?> verifyCode(@PathVariable String email, @PathVariable String code) {
        boolean result = emailRedisService.checkVerificationCode(email, code);
        SuccessResponse response = new SuccessResponse(true, SuccessEmailMessage.EMAIL_VERIFICATION_SUCCESS, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
