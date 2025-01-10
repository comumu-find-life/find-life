package com.api.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.common.login.response.LoginResponse;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.exception.NoDataException;
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

    /**
     * AccessToken(Jwt) 생성 메소드
     * <p>
     * AccessToken 에는 날짜와 이메일을 페이로드에 담습니다.
     * 사용할 알고리즘은 HMA512 알고리즘이고 application-core-dev.yml 에서 지정한 secret 키로 암호화
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * RefreshToken 생성
     * <p>
     * RefreshToken 은 클레임에 이메일을 넣지 않음
     */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }



    /**
     * 로그인 시 AccessToken 과 RefreshToken 을 헤더에 실어서 내보냄
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json"); // 응답의 컨텐츠 타입을 JSON으로 설정합니다.

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        SuccessResponse successResponse = new SuccessResponse(true, "Tokens generated successfully", loginResponse);
        try {
            // JSON 응답 본문을 응답에 작성합니다.
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
            // AccessToken 의 클레임에서 Email 을 추출하는 기능
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

    //  토큰 유효성을 검사하는 메서드
    public void isTokenValid(String token) throws AuthenticationException {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        } catch (Exception e) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
    }

    public void isRefreshTokenValid(String refreshToken) {
        try {
            Optional<User> byRefreshToken = userRepository.findByRefreshToken(refreshToken);
            byRefreshToken.get();
        }catch (Exception e){
            throw new NoDataException("존재하지 않는 RefreshToken 입니다.");
        }
    }

}

