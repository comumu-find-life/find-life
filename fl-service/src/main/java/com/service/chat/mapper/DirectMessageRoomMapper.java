package com.service.chat.mapper;

import com.service.home.mapper.HomeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DirectMessageRoomMapper {

    DirectMessageRoomMapper INSTANCE = Mappers.getMapper(DirectMessageRoomMapper.class);
}
