package com.service.user.mapper;

import com.core.user.model.User;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserSignupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    User toEntity(UserSignupRequest dto);

    @Mapping(target = "password", ignore = true)
    UserInformationDto toDto(User user);
}
