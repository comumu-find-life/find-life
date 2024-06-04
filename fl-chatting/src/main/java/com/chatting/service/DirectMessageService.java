package com.chatting.service;

import com.chatting.dto.DirectMessageDto;
import com.chatting.model.DirectMessage;
import com.chatting.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final DirectMessageRepository dmRepository;

    public String sendDM(DirectMessageDto dmDto) {

        try {
            DirectMessage save = dmRepository.save(
                    DirectMessage.builder()
                            .senderId(dmDto.getSenderId())
                            .receiverId(dmDto.getReceiverId())
                            .sentAt(LocalDateTime.now())
                            .message(dmDto.getMessage())
                            .build()
            );
            System.out.println(save.getId());
            return save.getId().toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
