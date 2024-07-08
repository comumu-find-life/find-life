package com.chatting.service;

import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import com.core.api_core.deal.model.DealState;
import com.core.chat_core.chat.model.DirectMessage;
import com.core.chat_core.chat.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final DirectMessageRepository dmRepository;

    public DirectMessageResponse sendDM(DirectMessageRequest dmDto) throws IllegalAccessException {
        try {
            System.out.println("TEST = " +dmDto.isDeal());
            DirectMessage save = dmRepository.save(
                    DirectMessage.builder()
                            .senderId(dmDto.getSenderId())
                            .receiverId(dmDto.getReceiverId())
                            .sentAt(LocalDateTime.now())
                            .isDeal(dmDto.isDeal())
                            .dealState(dmDto.getDealState())
                            .message(dmDto.getMessage())
                            .build()
            );
            return DirectMessageResponse.builder()
                    .message(save.getMessage())
                    .receiverId(save.getReceiverId())
                    .senderId(save.getSenderId())
                    .sentAt(save.getSentAt())
                    .build();
            //mapper.toResponse(save);
        } catch (Exception e) {
            throw new IllegalAccessException(e.getMessage());
        }
    }


    /**
     * 최근 대화 불러오기 (채팅방 입장시)
     *
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

    public List<DirectMessageResponse> findChatHistory(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findRecentLogs(user1Id, user2Id);
        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> DirectMessageResponse.builder()
                        .senderId(dm.getSenderId())
                        .receiverId(dm.getReceiverId())
                        .isDeal(dm.isDeal())
                        .dealState(dm.getDealState())
                        .message(dm.getMessage())
                        .sentAt(dm.getSentAt())
                        .build())
                .collect(Collectors.toList());
        return dmLogDtos;
    }
}
