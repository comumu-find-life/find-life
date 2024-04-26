package com.service.user.mapper;

import com.core.user.model.User;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserSignupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * (componentMode = 'spring') 을 안하면 빈 등록이 안된다. 꼭 해주자.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userPoint", ignore = true)
    User toEntity(UserSignupRequest dto);

    UserInformationDto toDto(User user);
}
