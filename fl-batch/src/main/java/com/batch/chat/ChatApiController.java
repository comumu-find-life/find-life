package com.batch.chat;

import com.common.chat.response.DirectMessageRoomListResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatApiService chatApiService;

    @PostMapping("/dm")
    public ResponseEntity<String> applicationDm(HttpServletRequest request, @RequestBody ApplicationDmFormRequest applicationDmFormRequest) {
        String token = getTokenFromCookie(request);
        chatApiService.applicationDm(applicationDmFormRequest, token);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/dm")
    public List<DirectMessageRoomListResponse> getRecentDmLogs(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        return chatApiService.findDmRoomsByUserId(token);
    }

    @GetMapping("/dm-rooms")
    public List<DirectMessageRoomListResponse> getDmRooms(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        return chatApiService.findDmRoomsByUserId(token);
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
