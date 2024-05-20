package com.service.chat;

import com.core.chat.dto.DirectMessageApplicationDto;
import com.core.chat.model.DirectMessageRoom;
import com.core.chat.repository.DirectMessageRoomRepository;
import com.service.chat.dto.DirectMessageDto;
import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final UserService userService;
    private final DirectMessageRoomRepository dmRoomRepository;

    @Value("${domain.chat}")
    String chatUrl;

    public void applicationDm(DirectMessageApplicationDto dmApplicationDto) {

        // 로그인 유저 정보 받아오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationDto user = userService.findByEmail(email);

        // 채팅방이 생성 되었는지 확인
        Optional<DirectMessageRoom> dmRoom = dmRoomRepository.findByUser1IdAndUser2Id();
        log.info(dmRoom.get().getUser1Id() + "");
        log.info(dmRoom.get().getUser2Id() + "");

        DirectMessageDto dmDto = DirectMessageDto.builder()
                .message(dmApplicationDto.getMessage())
                .receiverId(dmApplicationDto.getReceiverId())
                .senderId(user.getId()).build();



        RestTemplate restTemplate = new RestTemplate();

        String url = chatUrl + "/dm";

        restTemplate.postForObject(url, dmDto, Object.class);
    }
}
