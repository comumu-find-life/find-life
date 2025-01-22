package com.api.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.core.api_core.user.model.LoginResponse;
import com.core.api_core.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.common.utils.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String USER_ID = "userId";
    private static final String BEARER = "Bearer ";
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        SuccessResponse successResponse = new SuccessResponse(true, "Tokens generated successfully", loginResponse);
        try {
            String responseBody = objectMapper.writeValueAsString(successResponse);
            response.getWriter().write(responseBody);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("토큰을 응답 본문에 작성하는 동안 오류 발생: {}", e.getMessage());
        }

        log.info("Access Token, Refresh Token을 응답 본문에 설정 완료");
    }


    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }


    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }


    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public void isTokenValid(String token) throws AuthenticationException {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        } catch (Exception e) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
    }
}

