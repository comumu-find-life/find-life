package com.batch.chat;

import com.core.chat.model.DirectMessageRoom;
import com.service.chat.dto.DirectMessageRoomDto;
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
        String token = getTokenFromCookie(request);
        chatApiService.applicationDm(applicationDmFormDto, token);
    }

    @GetMapping("/dm-rooms")
    public void getDmRooms(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        chatApiService.findDmRoomsByUserId(token);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
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
