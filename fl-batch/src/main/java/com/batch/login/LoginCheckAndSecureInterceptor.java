package com.batch.login;


import com.service.user.dto.UserInformationDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

public class LoginCheckAndSecureInterceptor implements HandlerInterceptor {
    @Value("${domain.api-user}")
    private String LoginUserInfoUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        Cookie[] cookies = request.getCookies();

        // "Authorization" 쿠키 찾기
        if (cookies != null) {
            Cookie authCookie = Arrays.stream(cookies)
                    .filter(cookie -> "Authorization".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            // Authorization 쿠키가 있는지 확인
            if (authCookie == null) {
                response.sendRedirect("/login?redirectURL=" + requestURI);
                return false;
            }
            String jwtToken = authCookie.getValue();
            RestTemplate restTemplate = new RestTemplate();

            // HttpHeaders 객체 생성
            HttpHeaders headers = new HttpHeaders();
            // Bearer 토큰 추가
            headers.setBearerAuth(jwtToken);
            // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // 요청 보내기
            ResponseEntity<UserInformationDto> responseEntity = restTemplate.exchange(
                    "http://localhost:8080/v1/api/user/user-info",
                    HttpMethod.GET,
                    requestEntity,
                    UserInformationDto.class);

            // 응답 확인
            HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();

            System.out.println(statusCode == HttpStatus.OK);
            if (statusCode == HttpStatus.OK) {
                UserInformationDto responseBody = responseEntity.getBody();
                System.out.println("Response Body: " + responseBody);

                request.setAttribute("userId", responseBody.getId());
                request.setAttribute("accessToken", jwtToken);
                return true;
            } else {
                System.err.println("Request failed with status code: " + statusCode);
                return false;
            }

        }

        // Authorization 쿠키가 없으면 로그인 페이지로 리디렉션
        response.sendRedirect("/login?redirectURL=" + requestURI);
        return false;
    }

}
