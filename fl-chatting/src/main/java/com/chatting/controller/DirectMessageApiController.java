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

    @PostMapping(DM_SEND_FIRST_URL)
    public ResponseEntity<?> createDirectMessageRoom(@RequestBody final DirectMessageApplicationRequest dmDto) throws IllegalAccessException {
        Long roomId = dmService.createDirectMessageRoom(dmDto);
        SuccessResponse response = new SuccessResponse(true, DIRECT_MESSAGE_ROOM_CREATE_SUCCESS, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DM_HISTORY_URL)
    public ResponseEntity<?> findDirectMessageHistory(@RequestParam final Long user1Id, @RequestParam final Long user2Id) {
        List<DirectMessageResponse> chatHistory = dmService.findChatHistory(user1Id, user2Id);
        SuccessResponse response = new SuccessResponse(true, DIRECT_MESSAGE_HISTORY_FIND_SUCCESS, chatHistory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(DM_CHECK_READ_URL)
    public ResponseEntity<?> checkReadMessage(@RequestBody final DirectMessageReadRequest directMessageReadRequest){
        dmService.checkReadMessages(directMessageReadRequest);
        SuccessResponse response = new SuccessResponse(true, "", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(DM_FIND_ALL_ROOMS)
    public ResponseEntity<?> findDirectMessageList(@PathVariable final Long userId){
        List<DirectMessageRoomListResponse> directMessageRoomsByUser = dmService.getDirectMessageRoomsByUser(userId);
        SuccessResponse response = new SuccessResponse(true, DIRECT_MESSAGE_LIST_FIND_SUCCESS, directMessageRoomsByUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
