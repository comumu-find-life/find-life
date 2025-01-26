package com.chatting.service;

import com.core.exception.FcmException;
import com.core.mapper.DirectMessageMapper;
import com.core.mapper.DirectMessageRoomMapper;
import com.core.api_core.chat.dto.DirectMessageApplicationRequest;
import com.core.api_core.chat.dto.DirectMessageReadRequest;
import com.core.api_core.chat.dto.DirectMessageRequest;
import com.core.api_core.chat.dto.DirectMessageResponse;
import com.core.api_core.chat.dto.DirectMessageRoomListResponse;
import com.common.fcm.FCMHelper;
import com.common.fcm.FCMState;
import com.common.utils.OptionalUtil;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.repository.DirectMessageRoomRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.api_core.chat.model.DirectMessage;
import com.core.api_core.chat.repository.DirectMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.chatting.service.DirectMessageHelper.createDirectMessageRequest;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final FCMHelper fcmService;
    private final UserRepository userRepository;
    private final DirectMessageRoomRepository directMessageRoomRepository;
    private final DirectMessageRepository dmRepository;
    private final DirectMessageMapper mapper;
    private final DirectMessageRoomMapper directMessageRoomMapper;

    @Transactional
    public Long createDirectMessageRoom(final DirectMessageApplicationRequest request) {
        Long receiverId = request.getReceiverId();
        Long senderId = request.getSenderId();
        Long roomId = saveOrUpdateDirectMessageRoom(Math.min(senderId, receiverId), Math.max(senderId, receiverId), request);
        DirectMessageRequest directMessageRequest = createDirectMessageRequest(request.getMessage(), senderId, receiverId);
        saveDirectMessageAndPushNotication(toDirectMessage(directMessageRequest)).join(); //비동기 끝나고 실행
        return roomId;
    }

    @Async
    public CompletableFuture<DirectMessageResponse> saveDirectMessageAndPushNotication(final DirectMessage directMessage) {
        try {
            DirectMessage save = dmRepository.save(directMessage);
            User receiver = userRepository.findById(directMessage.getReceiverId()).get();
            User sender = userRepository.findById(directMessage.getSenderId()).get();
            String fcmToken = receiver.getFcmToken();
            fcmService.sendNotification(FCMState.NOT_SAVE, fcmToken, sender.getNickname(), directMessage.getMessage());
            return CompletableFuture.completedFuture(mapper.toDirectMessageResponse(save));
        } catch (Exception e) {
            throw new FcmException(e.getMessage());
        }
    }

    public DirectMessage toDirectMessage(final DirectMessageRequest dmDto){
        return mapper.toDirectMessage(dmDto);
    }

    public DirectMessageResponse toDirectMessageResponse(final DirectMessage directMessage){
        return mapper.toDirectMessageResponse(directMessage);
    }

    public DirectMessage getLastMessage(final Long user1Id, final Long user2Id) {
        return OptionalUtil.getOrElseThrow(dmRepository.findLastMessageByUserIds(user1Id, user2Id), "채팅 정보가 존재하지 않습니다.");
    }

    public List<DirectMessageRoomListResponse> getDirectMessageRoomsByUser(final Long userId) {
        List<DirectMessageRoom> rooms = directMessageRoomRepository.findByUser1IdOrUser2Id(userId);
        return rooms.stream()
                .map(room -> {
                    User otherUser = (room.getUser1().getId().equals(userId)) ? room.getUser2() : room.getUser1();
                    DirectMessage lastMessage = getLastMessage(userId, otherUser.getId());
                    int notReadCount = dmRepository.countNotReadMessage(userId, otherUser.getId());
                    return directMessageRoomMapper.toDirectMessageRoomListResponse(room, lastMessage, otherUser, notReadCount);
                })
                .collect(Collectors.toList());
    }


    public List<DirectMessageResponse> findChatHistory(final Long user1Id, final Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findDirectMessageByUserIds(user1Id, user2Id);
        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> mapper.toDirectMessageResponse(dm))
                .collect(Collectors.toList());
        return dmLogDtos;
    }


    @Transactional
    @Async
    public void checkReadMessages(final DirectMessageReadRequest directMessageReadRequest){
        dmRepository.markMessagesAsRead(directMessageReadRequest.getSenderId(), directMessageReadRequest.getReceiverId());
    }

    private Long saveOrUpdateDirectMessageRoom(final Long user1Id, final Long user2Id, final DirectMessageApplicationRequest request) {
        Optional<DirectMessageRoom> directMessageRoom = directMessageRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (directMessageRoom.isEmpty()) {
            User user1 = OptionalUtil.getOrElseThrow(userRepository.findById(user1Id), "user not found");
            User user2 = OptionalUtil.getOrElseThrow(userRepository.findById(user2Id), "user not found");
            DirectMessageRoom newRoom = DirectMessageHelper.createDirectMessageRoom(user1, user2, request.getRoomId());
            return directMessageRoomRepository.save(newRoom).getId();
        }
        DirectMessageRoom room = directMessageRoom.get();
        room.setProgressHomeId(request.getRoomId());
        return room.getId();
    }
}
