package com.service.chat;

import com.core.chat.dto.DirectMessageApplicationDto;
import com.core.chat.dto.DirectMessageRoomInfoDto;
import com.core.chat.model.DirectMessageRoom;
import com.core.chat.repository.DirectMessageRoomRepository;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.service.chat.dto.DirectMessageDto;
import com.service.chat.dto.DirectMessageRoomDto;
import com.service.chat.dto.DirectMessageRoomListDto;
import com.service.chat.mapper.DirectMessageRoomMapper;
import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final DirectMessageRoomRepository dmRoomRepository;
    private final DirectMessageRoomMapper dmRoomMapper;


    @Value("${domain.chat}")
    private String chatUrl;

    public void applicationDm(DirectMessageApplicationDto dmApplicationDto) {
        // 로그인 유저 정보 받아오기
        Long userId = getLoginUserId();

        // 채팅방 생성 (User1Id에 작은 값, User2Id에 큰 값을 항상 유지)
        saveDmRoom(Math.min(dmApplicationDto.getReceiverId(), userId), Math.max(dmApplicationDto.getReceiverId(), userId));

        // 채팅 전송
        DirectMessageDto dmDto = DirectMessageDto.builder()
                .message(dmApplicationDto.getMessage())
                .receiverId(dmApplicationDto.getReceiverId())
                .senderId(userId).build();

        RestTemplate restTemplate = new RestTemplate();
        String url = chatUrl + "/dm";
        restTemplate.postForObject(url, dmDto, Object.class);
    }

    // 로그인된 유저의 Dm리스트
    public List<DirectMessageRoomListDto> findDmRoomsByLoginUserId() {
        // 로그인 유저 정보 받아오기
        Long userId = getLoginUserId();
        List<DirectMessageRoom> dmRooms = dmRoomRepository.findByUser1IdOrUser2Id(userId);

        //Dto변환
        List<DirectMessageRoomListDto> dmRoomListDtos = dmRooms.stream().map(dmRoom -> {
            Long userSetId = (dmRoom.getUser1().getId() != userId) ? dmRoom.getUser1().getId() : dmRoom.getUser2().getId();
            String userSetName = (dmRoom.getUser1().getId() != userId) ? dmRoom.getUser1().getNickname() : dmRoom.getUser2().getNickname();
            return DirectMessageRoomListDto.builder().id(dmRoom.getId()).userId(userSetId).userName(userSetName).build();
        }).collect(Collectors.toList());

        return dmRoomListDtos;
    }

    public DirectMessageRoomInfoDto findDmRoomById(Long id) {
        DirectMessageRoom room = dmRoomRepository.findById(id).orElse(null);
        // TODO 잘못된 요청 처리
        return dmRoomTodmRooomInfoDto(room);
    }

    private DirectMessageRoomInfoDto dmRoomTodmRooomInfoDto(DirectMessageRoom room) {
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
        return DirectMessageRoomInfoDto.builder()
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
    private void saveDmRoom(Long user1Id, Long user2Id) {
        log.info("user1=" + user1Id);
        log.info("user2=" + user2Id);


        /**
         * todo
         * 왜 User Builder 로 객체 생성? db에서 조회해서 넣어야 하는거 아닌감
         */
        if (!dmRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id).isPresent()) {
            User user1 = userRepository.findById(user1Id).get();
            User user2 = userRepository.findById(user2Id).get();

            DirectMessageRoom newDmRoom = DirectMessageRoom.builder()
                    .user1(user1)
                    .user2(user2)
                    .build();

            dmRoomRepository.save(newDmRoom);
        }
    }

    // List<dmRoom> => List<dmRoomDto>
    private List<DirectMessageRoomDto> toDmRoomDtos(List<DirectMessageRoom> dmRooms) {

        List<DirectMessageRoomDto> dmRoomDtos = new ArrayList<>();
        dmRooms.stream()
                .forEach(room -> {
                    dmRoomDtos.add(dmRoomMapper.toDto(room));
                });
        return dmRoomDtos;
    }
}
