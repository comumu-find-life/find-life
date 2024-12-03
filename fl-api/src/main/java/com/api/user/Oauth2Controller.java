package com.api.user;

import com.api.security.service.JwtService;
import com.common.login.response.LoginResponse;
import com.common.user.request.GoogleAuthRequest;
import com.common.utils.SuccessResponse;
import com.nimbusds.jose.JOSEException;
import com.service.user.AppleAuthService;
import com.service.user.GoogleAuthService;
import com.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import static com.api.config.ApiUrlConstants.APPLE_LOGIN_URL;
import static com.api.config.ApiUrlConstants.GOOGLE_LOGIN_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class Oauth2Controller {

    private final AppleAuthService appleAuthService;
    private final UserService userService;
    private final GoogleAuthService googleAuthService;
    private final JwtService jwtService;

    @PostMapping(GOOGLE_LOGIN_URL)
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody GoogleAuthRequest request) throws GeneralSecurityException, IOException {
        String email = googleAuthService.getGoogleEmail(request);
        if (!userService.isExistAccountByEmail(email)) {
            SuccessResponse successResponse = new SuccessResponse(false, "회원가입을 진행해주세요", email);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        SuccessResponse successResponse = new SuccessResponse(true, "Tokens generated successfully", createLoginResponse(email));
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping(APPLE_LOGIN_URL)
    public ResponseEntity<?> authenticateWithApple(@RequestBody String identityToken) throws IOException, ParseException, JOSEException {
        String email = appleAuthService.getAppleEmail(identityToken);
        if (!userService.isExistAccountByEmail(email)) {
            SuccessResponse successResponse = new SuccessResponse(false, "회원가입을 진행해주세요", email);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        SuccessResponse successResponse = new SuccessResponse(true, "Tokens generated successfully", createLoginResponse(email));
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    private LoginResponse createLoginResponse(String email) {
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
