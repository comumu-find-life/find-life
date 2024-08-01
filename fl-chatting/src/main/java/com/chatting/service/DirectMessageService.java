package com.chatting.service;

import com.common.chat.mapper.DirectMessageMapper;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import com.core.chat_core.chat.model.DirectMessage;
import com.core.chat_core.chat.repository.DirectMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final DirectMessageRepository dmRepository;
    private final DirectMessageMapper mapper;

    /**
     * 채팅
     */
    public DirectMessageResponse sendDM(DirectMessageRequest dmDto) throws IllegalAccessException {
        try {
            DirectMessage directMessage = mapper.toDirectMessage(dmDto);
            DirectMessage save = dmRepository.save(directMessage);
            return mapper.toDirectMessageResponse(save);
        } catch (Exception e) {
            throw new IllegalAccessException(e.getMessage());
        }
    }

    /**
     * 최근 대화 불러오기 (채팅방 입장시)
     */
    public List<DirectMessageResponse> findRecentChatLog(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findRecentLogs(user1Id, user2Id);

        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> mapper.toDirectMessageResponse(dm))
                .collect(Collectors.toList());
        return dmLogDtos;
    }

    public List<DirectMessageResponse> findChatHistory(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findRecentLogs(user1Id, user2Id);
        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> mapper.toDirectMessageResponse(dm))
                .collect(Collectors.toList());
        return dmLogDtos;
    }
}
