package com.common.chat.mapper;

import com.common.chat.response.DirectMessageRoomInfoResponse;
import com.core.api_core.chat.model.DirectMessageRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectMessageRoomMapper {

    DirectMessageRoomMapper INSTANCE = Mappers.getMapper(DirectMessageRoomMapper.class);

    @Mapping(target = "id", expression = "java(directMessageRoom.getId())")
    @Mapping(target = "senderId", expression = "java(directMessageRoom.getUser1().getId())")
    @Mapping(target = "senderName", expression = "java(directMessageRoom.getUser1().getNickname())")
    @Mapping(target = "receiverId", expression = "java(directMessageRoom.getUser2().getId())")
    @Mapping(target = "receiverName", expression = "java(directMessageRoom.getUser2().getNickname())")
    DirectMessageRoomInfoResponse toDirectMessageRoomInfoResponse(DirectMessageRoom directMessageRoom);


}
