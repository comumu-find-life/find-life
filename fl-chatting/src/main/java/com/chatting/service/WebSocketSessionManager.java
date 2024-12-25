package com.chatting.service;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final Map<String, Long> sessionRoomMap = new ConcurrentHashMap<>();
    private final Map<String, Long> sessionUserMap = new ConcurrentHashMap<>();

    // 세션 추가
    public void addSession(String sessionId, Long roomId, Long userId) {
        sessionRoomMap.put(sessionId, roomId);
        sessionUserMap.put(sessionId, userId);
    }

    // 세션 삭제
    public void removeSession(String sessionId) {
        sessionRoomMap.remove(sessionId);
        sessionUserMap.remove(sessionId);
    }

    // 세션 ID로 룸 ID 조회
    public Long getRoomId(String sessionId) {
        return sessionRoomMap.get(sessionId);
    }

    // 세션 ID로 사용자 ID 조회
    public Long getUserId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

    // 특정 사용자가 방에 있는지 확인
    public boolean isUserInRoom(Long roomId, Long userId) {
        return sessionRoomMap.entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(roomId) &&
                        userId.equals(sessionUserMap.get(entry.getKey())));
    }
}