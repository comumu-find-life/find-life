package com.common.chat.mapper;

import com.common.chat.response.DirectMessageRoomDto;
import com.core.chat.model.DirectMessageRoom;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectMessageRoomMapper {

    DirectMessageRoomMapper INSTANCE = Mappers.getMapper(DirectMessageRoomMapper.class);

    DirectMessageRoomDto toDto(DirectMessageRoom directMessageRoom);

}
