package com.common.chat.mapper;

import com.common.chat.response.DirectMessageRoomInfoResponse;
import com.common.chat.response.DirectMessageRoomListResponse;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.user.model.User;
import com.core.api_core.chat.model.DirectMessage;
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


    @Mapping(target = "id", expression = "java(room.getId())")
    @Mapping(target = "otherUserId" ,expression = "java(otherUser.getId())")
    @Mapping(target = "userNickname",expression = "java(otherUser.getNickname())")
    @Mapping(target = "userProfileUrl",expression ="java(otherUser.getProfileUrl())")
    @Mapping(target = "progressHomeId",expression = "java(room.getProgressHomeId())")
    @Mapping(target = "lastMessage" ,expression = "java(lastMessage.getMessage())")
    @Mapping(target = "lastSendDateTime",expression = "java(lastMessage.getSentAt())")
    DirectMessageRoomListResponse toDirectMessageRoomListResponse(DirectMessageRoom room, DirectMessage lastMessage, User otherUser);


}
