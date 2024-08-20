package com.service.chat;

import com.common.chat.mapper.DirectMessageRoomMapper;
import com.common.chat.request.DirectMessageApplicationRequest;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageRoomInfoResponse;
import com.common.chat.response.DirectMessageRoomListResponse;
import com.common.user.response.UserInformationResponse;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.repository.DirectMessageRoomRepository;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.service.user.UserService;
import com.service.utils.OptionalUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectMessageRoomService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final DirectMessageRoomRepository directMessageRoomRepository;
    private final DirectMessageRoomMapper directMessageRoomMapper;

    @Value("${domain.chat}")
    private String chatUrl;

    /**
     * 채팅방 생성 메서드
     */
    @Transactional
    public Long createDirectMessageRoom(DirectMessageApplicationRequest request) {
        Long senderId = getLoggedInUserId();
        Long receiverId = request.getReceiverId();

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same.");
        }

        Long roomId = saveOrUpdateDirectMessageRoom(Math.min(senderId, receiverId), Math.max(senderId, receiverId), request);

        sendDirectMessage(request.getMessage(), senderId, receiverId);

        return roomId;
    }

    /**
     * 거래 완료 메시지 전송 메서드
     */
    @Transactional
    public void sendDealCompletionMessage(DirectMessageRequest request) {
        DirectMessageRequest dealCompletionMessage = createDealCompletionMessage(request);
        sendDirectMessage(dealCompletionMessage);
    }

    /**
     * 로그인된 유저의 채팅방 목록 조회
     * todo 마지막 메시지 입력
     */
    public List<DirectMessageRoomListResponse> getDirectMessageRoomsByUser() {
        Long userId = getLoggedInUserId();
        List<DirectMessageRoom> rooms = directMessageRoomRepository.findByUser1IdOrUser2Id(userId);

        return rooms.stream()
                .map(room -> toDirectMessageRoomListResponse(room, userId))
                .collect(Collectors.toList());
    }

    /**
     * 채팅방 ID로 채팅방 정보 조회
     */
    public DirectMessageRoomInfoResponse getDirectMessageRoomById(Long id) {
        DirectMessageRoom room = OptionalUtil.getOrElseThrow(directMessageRoomRepository.findById(id), "존재하지 않는 채팅 방 ID입니다.");
        return directMessageRoomMapper.toDirectMessageRoomInfoResponse(room);
    }

    /**
     * 채팅방 저장 또는 업데이트 메서드
     */
    private Long saveOrUpdateDirectMessageRoom(Long user1Id, Long user2Id, DirectMessageApplicationRequest request) {
        Optional<DirectMessageRoom> existingRoom = directMessageRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (existingRoom.isEmpty()) {
            User user1 = userRepository.findById(user1Id).orElseThrow(() -> new IllegalArgumentException("User not found: " + user1Id));
            User user2 = userRepository.findById(user2Id).orElseThrow(() -> new IllegalArgumentException("User not found: " + user2Id));

            DirectMessageRoom newRoom = DirectMessageRoom.builder()
                    .user1(user1)
                    .user2(user2)
                    .progressHomeId(request.getRoomId())
                    .build();

            return directMessageRoomRepository.save(newRoom).getId();
        } else {
            DirectMessageRoom room = existingRoom.get();
            room.setProgressHomeId(request.getRoomId());
            return room.getId();
        }
    }

    /**
     * 거래 완료 메시지 생성 메서드
     */
    private DirectMessageRequest createDealCompletionMessage(DirectMessageRequest request) {
        return DirectMessageRequest.builder()
                .message("DEAL MESSAGE")
                .receiverId(request.getReceiverId())
                .isDeal(3)
                .senderId(request.getSenderId())
                .dealState(DealState.COMPLETE_DEAL)
                .roomId(request.getRoomId())
                .build();
    }

    /**
     * 채팅 메시지 전송 메서드
     */
    private void sendDirectMessage(String message, Long senderId, Long receiverId) {
        DirectMessageRequest messageRequest = DirectMessageRequest.builder()
                .message(message)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        sendDirectMessage(messageRequest);
    }

    /**
     * 채팅 메시지 전송 메서드 (오버로드)
     */
    private void sendDirectMessage(DirectMessageRequest messageRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = chatUrl + "/dm";
        restTemplate.postForObject(url, messageRequest, Object.class);
    }

    /**
     * DirectMessageRoomListResponse 생성 메서드
     */
    private DirectMessageRoomListResponse toDirectMessageRoomListResponse(DirectMessageRoom room, Long userId) {
        User otherUser = (room.getUser1().getId().equals(userId)) ? room.getUser2() : room.getUser1();
        return DirectMessageRoomListResponse.builder()
                .id(room.getId())
                .progressHomeId(room.getProgressHomeId())
                .userId(otherUser.getId())
                .userNickname(otherUser.getNickname())
                .userProfileUrl(otherUser.getProfileUrl())
                .lastMessage("TEST MESSAGE")
                .build();
    }

    /**
     * 현재 로그인된 사용자 ID 가져오는 메서드
     */
    private Long getLoggedInUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationResponse user = userService.findByEmail(email);
        return user.getId();
    }
}
