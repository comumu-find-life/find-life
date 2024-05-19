package com.batch.login;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.HttpCookie;
import java.util.Arrays;

public class LoginCheckInterceptor implements HandlerInterceptor {

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


            return true;

        }

        // Authorization 쿠키가 없으면 로그인 페이지로 리디렉션
        response.sendRedirect("/login?redirectURL=" + requestURI);
        return false;
    }
}
