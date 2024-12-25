package com.chatting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class SessionEventListener {

    private final WebSocketSessionManager sessionManager;

    // 세션 연결 이벤트 처리
    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Long roomId = Long.valueOf(headerAccessor.getNativeHeader("roomId").get(0));
        Long userId = Long.valueOf(headerAccessor.getNativeHeader("userId").get(0));
        sessionManager.addSession(headerAccessor.getSessionId(), roomId, userId);
    }

    // 세션 종료 이벤트 처리
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        System.out.println("Disconnect Listener");

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId(); // 세션 ID만 추출

        // 세션 정보 조회
        Long roomId = sessionManager.getRoomId(sessionId);
        Long userId = sessionManager.getUserId(sessionId);

        if (roomId != null && userId != null) {
            System.out.printf("Disconnected User ID: %d, Room ID: %d%n", userId, roomId);
        } else {
            System.out.printf("Disconnected Session ID: %s%n", sessionId);
        }

        // 세션 삭제
        sessionManager.removeSession(sessionId);
    }
}