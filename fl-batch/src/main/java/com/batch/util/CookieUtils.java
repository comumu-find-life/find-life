package com.batch.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class CookieUtils {

    public static String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie authCookie = Arrays.stream(cookies)
                    .filter(cookie -> "Authorization".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            // Authorization 쿠키가 있는지 확인
            if (authCookie != null) {
                return authCookie.getValue();
            }
        }
        return null;
    }
}
