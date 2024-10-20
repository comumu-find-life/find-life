package com.chatting.service;

import com.common.chat.mapper.DirectMessageMapper;
import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import com.common.utils.OptionalUtil;
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
     * 채팅 전송 service
     */
    public DirectMessageResponse sendDM(DirectMessageRequest dmDto) throws IllegalAccessException {
        try {
            System.out.println("###");
            DirectMessage directMessage = mapper.toDirectMessage(dmDto);
            System.out.println("111");
            DirectMessage save = dmRepository.save(directMessage);
            System.out.println("333");
            return mapper.toDirectMessageResponse(save);
        } catch (Exception e) {
            throw new IllegalAccessException(e.getMessage());
        }
    }

    public DirectMessage getLastMessage(Long user1Id, Long user2Id) {
        return  OptionalUtil.getOrElseThrow(dmRepository.findLastMessageByUserIds(user1Id, user2Id), "채팅 정보가 존재하지 않습니다.");

    }

    /**
     * 최근 대화 불러오기 (채팅방 입장시)
     */
    public List<DirectMessageResponse> findRecentChatLog(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findDirectMessageByUserIds(user1Id, user2Id);

        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> mapper.toDirectMessageResponse(dm))
                .collect(Collectors.toList());
        return dmLogDtos;
    }

    public List<DirectMessageResponse> findChatHistory(Long user1Id, Long user2Id) {
        List<DirectMessage> dmLogs = dmRepository.findDirectMessageByUserIds(user1Id, user2Id);
        List<DirectMessageResponse> dmLogDtos = dmLogs.stream()
                .map(dm -> mapper.toDirectMessageResponse(dm))
                .collect(Collectors.toList());
        return dmLogDtos;
    }
}
