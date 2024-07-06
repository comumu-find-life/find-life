package com.chatting.service;

import com.chatting.dto.DirectMessageDto;
import com.chatting.dto.DirectMessageResponse;
import com.chatting.model.DirectMessage;
import com.chatting.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
            return save.getId().toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 최근 대화 불러오기 (채팅방 입장시)
     * @return
     */
    public List<DirectMessageResponse> findRecentChatLog(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findRecentLogs(user1Id, user2Id);

        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> DirectMessageResponse.builder()
                        .senderId(dm.getSenderId())
                        .receiverId(dm.getReceiverId())
                        .message(dm.getMessage())
                        .sentAt(dm.getSentAt())
                        .build())
                .collect(Collectors.toList());
        return dmLogDtos;
    }

    public List<DirectMessageDto> findChatHistory(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findRecentLogs(user1Id, user2Id);
        List<DirectMessageDto> dmLogDtos = dmLogs.stream()
                .map(dm -> DirectMessageDto.builder()
                        .senderId(dm.getSenderId())
                        .receiverId(dm.getReceiverId())
                        .message(dm.getMessage())
                        .sentAt(dm.getSentAt())
                        .build())
                .collect(Collectors.toList());
        return dmLogDtos;
    }
}
