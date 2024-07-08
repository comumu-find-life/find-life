package com.service.chat;

import com.common.chat.request.DirectMessageApplicationRequest;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageRoomInfoResponse;
import com.common.chat.response.DirectMessageRoomListResponse;
import com.common.user.response.UserInformationDto;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.repository.DirectMessageRoomRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.service.user.UserService;
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
    private final DirectMessageRoomRepository dmRoomRepository;

    @Value("${domain.chat}")
    private String chatUrl;

    @Transactional
    public Long applicationDm(DirectMessageApplicationRequest dmApplicationDto) {
        // 로그인 유저 정보 받아오기
        Long userId = getLoginUserId();
        log.info(userId + "");
        log.info(dmApplicationDto.getReceiverId() + "");

        // 채팅방 생성 (User1Id에 작은 값, User2Id에 큰 값을 항상 유지)
        Long roomId = saveDmRoom(Math.min(dmApplicationDto.getReceiverId(), userId), Math.max(dmApplicationDto.getReceiverId(), userId), dmApplicationDto);

        //todo 이미 생성된 방이 있다면, DirectMessageRoom 에 있는 progressHomeId 최신화

        // 채팅 전송
        DirectMessageRequest dmDto = DirectMessageRequest.builder()
                .message(dmApplicationDto.getMessage())
                .receiverId(dmApplicationDto.getReceiverId())
                .senderId(userId).build();

        RestTemplate restTemplate = new RestTemplate();

        String url = chatUrl + "/dm";
        restTemplate.postForObject(url, dmDto, Object.class);
        return roomId;
    }

    // 로그인된 유저의 Dm리스트
    public List<DirectMessageRoomListResponse> findDmRoomsByLoginUserId() {
        // 로그인 유저 정보 받아오기
        Long userId = getLoginUserId();
        List<DirectMessageRoom> dmRooms = dmRoomRepository.findByUser1IdOrUser2Id(userId);


        // Dto변환
        List<DirectMessageRoomListResponse> dmRoomListDtos = dmRooms.stream().map(dmRoom -> {
            //DirectMessage message = dmRepository.findLastMessage(dmRoom.getUser1().getId(), dmRoom.getUser2().getId());
            return getChatUserInfo(dmRoom.getId(), (dmRoom.getUser1().getId() != userId) ? dmRoom.getUser1() : dmRoom.getUser2(), dmRoom.getProgressHomeId(), "TEST MESSAGE");
        }).collect(Collectors.toList());
        return dmRoomListDtos;
    }

    public DirectMessageRoomInfoResponse findDmRoomById(Long id) {
        DirectMessageRoom room = dmRoomRepository.findById(id).orElse(null);
        // TODO 잘못된 요청 처리
        return dmRoomTodmRooomInfoDto(room);
    }

    private DirectMessageRoomInfoResponse dmRoomTodmRooomInfoDto(DirectMessageRoom room) {
        Long userId = getLoginUserId();
        Long senderSetId = room.getUser1().getId();
        String senderSetName = room.getUser1().getNickname();
        Long receiverSetId = room.getUser2().getId();
        String receiverSetName = room.getUser2().getNickname();
        if (userId != room.getUser1().getId()) {
            senderSetId = room.getUser2().getId();
            senderSetName = room.getUser2().getNickname();
            receiverSetId = room.getUser1().getId();
            receiverSetName = room.getUser1().getNickname();
        }
        return DirectMessageRoomInfoResponse.builder()
                .id(room.getId())
                .senderId(senderSetId)
                .senderName(senderSetName)
                .receiverId(receiverSetId)
                .receiverName(receiverSetName)
                .build();
    }

    private Long getLoginUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationDto user = userService.findByEmail(email);
        return user.getId();
    }

    // User1, User2 간의 채팅방이 이미 존재하지 않다면 생성
    private Long saveDmRoom(Long user1Id, Long user2Id, DirectMessageApplicationRequest directMessageApplicationDto) {
        Optional<DirectMessageRoom> byUser1IdAndUser2Id = dmRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id);
        if (byUser1IdAndUser2Id.isEmpty()) {
            User user1 = userRepository.findById(user1Id).get();
            User user2 = userRepository.findById(user2Id).get();

            DirectMessageRoom newDmRoom = DirectMessageRoom.builder()
                    .user1(user1)
                    .user2(user2)
                    .progressHomeId(directMessageApplicationDto.getRoomId())
                    .build();

            return dmRoomRepository.save(newDmRoom).getId();
        } else {
            byUser1IdAndUser2Id.get().setProgressHomeId(directMessageApplicationDto.getRoomId());
            return byUser1IdAndUser2Id.get().getId();
        }

    }

    /**
     * todo 필요한 메서드인지 ?~?
     */
//    private List<DirectMessageRoomResponse> toDmRoomDtos(List<DirectMessageRoom> dmRooms) {
//
//        List<DirectMessageRoomResponse> dmRoomDtos = new ArrayList<>();
//        dmRooms.stream()
//                .forEach(room -> {
//                    dmRoomDtos.add(dmRoomMapper.toDto(room));
//                });
//        return dmRoomDtos;
//    }
    private DirectMessageRoomListResponse getChatUserInfo(Long id, User user, Long homeId, String lastMessage) {
        return DirectMessageRoomListResponse.builder()
                .id(id)
                .progressHomeId(homeId)
                .userId(user.getId())
                .userNickname(user.getNickname())
                .userProfileUrl(user.getProfileUrl())
                .lastMessage(lastMessage)
                .build();
    }
}
