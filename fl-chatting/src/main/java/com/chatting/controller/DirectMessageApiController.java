package com.chatting.controller;

import com.chatting.service.DirectMessageService;
import com.common.chat.request.DirectMessageApplicationRequest;
import com.common.chat.request.DirectMessageReadRequest;
import com.common.chat.response.DirectMessageResponse;
import com.common.chat.response.DirectMessageRoomListResponse;
import com.common.fcm.FCMHelper;
import com.common.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.chatting.config.ApiUrlConstants.*;
import static com.chatting.controller.SuccessDirectMessages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class DirectMessageApiController {

    private final DirectMessageService dmService;
    private final FCMHelper fcmService;

    /**
     * todo 삭제
     */
    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody String token) throws IllegalAccessException {
        fcmService.sendNotification("ex2ePZth7UP7jERExgXVwM:APA91bG8iurSUOdzxmYm9liHAriU8PrU5e14fRORcW5X-YiS3c4z86M8-sPV3zPX_ZEmdt5VuQKIFXIQ9horKqq80uo0j3hv61va2_6KqXI4o-cvCh0bxX8", "title", "body");
        SuccessResponse response = new SuccessResponse(true, "테스트", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 첫 채팅 전송 API
     */
    @PostMapping(DM_SEND_FIRST_URL)
    public ResponseEntity<?> createDirectMessageRoom(@RequestBody DirectMessageApplicationRequest dmDto) throws IllegalAccessException {
        Long roomId = dmService.createDirectMessageRoom(dmDto);
        SuccessResponse response = new SuccessResponse(true, DIRECT_MESSAGE_ROOM_CREATE_SUCCESS, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 두 사용자가 주고 받은 채팅 정보 모두 조회 API
     */
    @GetMapping(DM_HISTORY_URL)
    public ResponseEntity<?> findDirectMessageHistory(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        List<DirectMessageResponse> chatHistory = dmService.findChatHistory(user1Id, user2Id);
        SuccessResponse response = new SuccessResponse(true, DIRECT_MESSAGE_HISTORY_FIND_SUCCESS, chatHistory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(DM_CHECK_READ_URL)
    public ResponseEntity<?> checkReadMessage(@RequestBody DirectMessageReadRequest directMessageReadRequest){
        dmService.checkReadMessages(directMessageReadRequest);
        SuccessResponse response = new SuccessResponse(true, "", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 자신의 모든 채팅 목록 조회
     */
    @GetMapping(DM_FIND_ALL_ROOMS)
    public ResponseEntity<?> findDirectMessageList(@PathVariable Long userId){
        //todo 각 채팅방 별로 읽지 않은 메시지 개수 조회
        List<DirectMessageRoomListResponse> directMessageRoomsByUser = dmService.getDirectMessageRoomsByUser(userId);
        SuccessResponse response = new SuccessResponse(true, DIRECT_MESSAGE_LIST_FIND_SUCCESS, directMessageRoomsByUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
