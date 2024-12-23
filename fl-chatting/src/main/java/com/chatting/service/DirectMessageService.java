package com.chatting.service;

import com.common.chat.mapper.DirectMessageMapper;
import com.common.chat.mapper.DirectMessageRoomMapper;
import com.common.chat.request.DirectMessageApplicationRequest;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import com.common.chat.response.DirectMessageRoomListResponse;
import com.common.fcm.FCMHelper;
import com.common.utils.OptionalUtil;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.repository.DirectMessageRoomRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.api_core.chat.model.DirectMessage;
import com.core.api_core.chat.repository.DirectMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.chatting.service.DirectMessageHandler.createDirectMessageRequest;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final FCMHelper fcmService;
    private final UserRepository userRepository;
    private final DirectMessageRoomRepository directMessageRoomRepository;
    private final DirectMessageRepository dmRepository;
    private final DirectMessageMapper mapper;
    private final DirectMessageRoomMapper directMessageRoomMapper;

    /**
     * 채팅방 생성
     */
    @Transactional
    public Long createDirectMessageRoom(DirectMessageApplicationRequest request) throws IllegalAccessException {
        Long receiverId = request.getReceiverId();
        Long senderId = request.getSenderId();
        Long roomId = saveOrUpdateDirectMessageRoom(Math.min(senderId, receiverId), Math.max(senderId, receiverId), request);
        DirectMessageRequest directMessageRequest = createDirectMessageRequest(request.getMessage(), senderId, receiverId);
        sendDM(directMessageRequest);
        return roomId;
    }

    /**
     * 채팅 전송
     */
    public DirectMessageResponse sendDM(DirectMessageRequest dmDto) throws IllegalAccessException {
        try {
            DirectMessage directMessage = mapper.toDirectMessage(dmDto);
            DirectMessage save = dmRepository.save(directMessage);
            User receiver = userRepository.findById(dmDto.getReceiverId()).get();
            User sender = userRepository.findById(dmDto.getSenderId()).get();
            String fcmToken = receiver.getFcmToken();
            fcmService.sendNotification(fcmToken, sender.getNickname(), dmDto.getMessage());
            return mapper.toDirectMessageResponse(save);
        } catch (Exception e) {
            throw new IllegalAccessException(e.getMessage());
        }
    }

    public DirectMessage getLastMessage(Long user1Id, Long user2Id) {
        return OptionalUtil.getOrElseThrow(dmRepository.findLastMessageByUserIds(user1Id, user2Id), "채팅 정보가 존재하지 않습니다.");

    }

    /**
     * 자신이 속한 채팅방 리스트 조회 메서드
     */
    public List<DirectMessageRoomListResponse> getDirectMessageRoomsByUser(Long userId) {
        List<DirectMessageRoom> rooms = directMessageRoomRepository.findByUser1IdOrUser2Id(userId);
        return rooms.stream()
                .map(room -> {
                    User otherUser = (room.getUser1().getId().equals(userId)) ? room.getUser2() : room.getUser1();
                    DirectMessage lastMessage = getLastMessage(userId, otherUser.getId());
                    return directMessageRoomMapper.toDirectMessageRoomListResponse(room, lastMessage, otherUser);
                })
                .collect(Collectors.toList());
    }


    /**
     * 두명 사용자의 채팅 내역 조회 메서드
     */
    public List<DirectMessageResponse> findChatHistory(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findDirectMessageByUserIds(user1Id, user2Id);
        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> mapper.toDirectMessageResponse(dm))
                .collect(Collectors.toList());
        return dmLogDtos;
    }


    private Long saveOrUpdateDirectMessageRoom(Long user1Id, Long user2Id, DirectMessageApplicationRequest request) {
        Optional<DirectMessageRoom> directMessageRoom = directMessageRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (directMessageRoom.isEmpty()) {
            User user1 = userRepository.findById(user1Id).orElseThrow(() -> new IllegalArgumentException("User not found: " + user1Id));
            User user2 = userRepository.findById(user2Id).orElseThrow(() -> new IllegalArgumentException("User not found: " + user2Id));
            DirectMessageRoom newRoom = DirectMessageHandler.createDirectMessageRoom(user1, user2, request.getRoomId());
            return directMessageRoomRepository.save(newRoom).getId();
        }
        DirectMessageRoom room = directMessageRoom.get();
        room.setProgressHomeId(request.getRoomId());
        return room.getId();
    }
}
