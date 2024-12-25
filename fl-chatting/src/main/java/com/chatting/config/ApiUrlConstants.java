package com.chatting.config;

public class ApiUrlConstants {
    public static final String BASE_API_URL = "/v1/api";

    // 첫 채팅방 생성
    public static final String DM_SEND_FIRST_URL = BASE_API_URL + "/direct-messages";

    // 두 사용자의 채팅 기록 조회
    public static final String DM_HISTORY_URL = BASE_API_URL + "/direct-messages/history";

    public static final String DM_CHECK_READ_URL = BASE_API_URL + "/direct-messages/read";

    // 사용자의 모든 채팅방 목록 조회
    public static final String DM_FIND_ALL_ROOMS = BASE_API_URL + "/users/{userId}/direct-message-rooms";
}
