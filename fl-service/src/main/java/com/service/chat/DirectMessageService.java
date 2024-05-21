package com.service.chat;

import com.core.chat.dto.DirectMessageApplicationDto;
import com.core.chat.model.DirectMessageRoom;
import com.core.chat.repository.DirectMessageRoomRepository;
import com.service.chat.dto.DirectMessageDto;
import com.service.chat.dto.DirectMessageRoomDto;
import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final UserService userService;
    private final DirectMessageRoomRepository dmRoomRepository;

    @Value("${domain.chat}")
    private String chatUrl;

    public void applicationDm(DirectMessageApplicationDto dmApplicationDto) {

        // 로그인 유저 정보 받아오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationDto user = userService.findByEmail(email);

        // 채팅방 생성 (User1Id에 작은 값, User2Id에 큰 값을 항상 유지)
        saveDmRoom(Math.min(dmApplicationDto.getReceiverId(), user.getId()), Math.max(dmApplicationDto.getReceiverId(), user.getId()));

        // 채팅 전송
        DirectMessageDto dmDto = DirectMessageDto.builder()
                .message(dmApplicationDto.getMessage())
                .receiverId(dmApplicationDto.getReceiverId())
                .senderId(user.getId()).build();

        RestTemplate restTemplate = new RestTemplate();
        String url = chatUrl + "/dm";
        restTemplate.postForObject(url, dmDto, Object.class);
    }

    public List<DirectMessageRoomDto> findDmRoomsByLoginUserId() {
        // 로그인 유저 정보 받아오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationDto user = userService.findByEmail(email);

        return dmRoomRepository.findByUser1IdOrUser2Id(user.getId());
    }


    // User1, User2 간의 채팅방이 이미 존재하지 않다면 생성
    private void saveDmRoom(Long user1Id, Long user2Id) {
        log.info(!dmRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id).isPresent() + "");
        log.info(dmRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id).get().getUser1Id() + "");

        if (!dmRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id).isPresent()) {
            DirectMessageRoom newDmRoom = DirectMessageRoom.builder().user1Id(user1Id).user2Id(user2Id).build();
            dmRoomRepository.save(newDmRoom);
        }
    }
}
