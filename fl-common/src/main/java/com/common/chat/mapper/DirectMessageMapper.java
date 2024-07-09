package com.common.chat.mapper;

import com.common.chat.request.DirectMessageRequest;
import com.common.chat.response.DirectMessageResponse;
import com.common.chat.response.DirectMessageRoomResponse;
import com.core.chat_core.chat.model.DirectMessage;
import com.core.api_core.chat.model.DirectMessageRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectMessageMapper {

    DirectMessageMapper INSTANCE = Mappers.getMapper(DirectMessageMapper.class);

    /**
     * DirectMessageRequest --> DirectMessage
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentAt", expression = "java(java.time.LocalDateTime.now())")
    DirectMessage toDirectMessage(DirectMessageRequest request);

    /**
     * DirectMessage --> DirectMessageResponse
     */
    DirectMessageResponse toDirectMessageResponse(DirectMessage entity);


}
