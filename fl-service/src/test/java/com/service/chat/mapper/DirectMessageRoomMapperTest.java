package com.service.chat.mapper;

import com.core.chat.model.DirectMessageRoom;
import com.service.chat.dto.DirectMessageRoomDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DirectMessageRoomMapperTest {


    private final DirectMessageRoomMapper directMessageRoomMapper;

    DirectMessageRoomMapperTest(DirectMessageRoomMapper directMessageRoomMapper) {
        this.directMessageRoomMapper = directMessageRoomMapper;
    }

    @Test
    void to_Dto_Test() {
        DirectMessageRoom dmRoom = DirectMessageRoom.builder().id(1L).user2Id(1L).user2Id(2L).build();
        DirectMessageRoomDto dmRoomDto = directMessageRoomMapper.toDto(dmRoom);

        Assertions.assertThat(dmRoom.getId()).isEqualTo(dmRoomDto.getId());
        Assertions.assertThat(dmRoom.getUser1Id()).isEqualTo(dmRoomDto.getUser1Id());
        Assertions.assertThat(dmRoom.getUser2Id()).isEqualTo(dmRoomDto.getUser2Id());
    }
}