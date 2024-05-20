package com.batch.chat;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatApiService chatApiService;

    @PostMapping("/dm")
    public void applicationDm(HttpServletRequest request, @ModelAttribute ApplicationDmFormDto applicationDmFormDto) {
        String token;
        Cookie[] cookies = request.getCookies();
        log.info(cookies.length + "");
        if (cookies != null) {
            log.info("Cookie is not null");
            Cookie authCookie = Arrays.stream(cookies)
                    .filter(cookie -> "Authorization".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);

            // Authorization 쿠키가 있는지 확인
            if (authCookie != null) {
                log.info("token=" + authCookie.getValue());
                token = authCookie.getValue();
            } else {
                return;
            }

        } else {
            return;
        }

        chatApiService.applicationDm(applicationDmFormDto, token);
    }
}
