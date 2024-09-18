package com.common.user.mapper;

import com.common.user.request.UserProfileUpdateRequest;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationByAdminResponse;
import com.common.user.response.UserInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.core.api_core.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * (componentMode = 'spring') 을 안하면 빈 등록이 안된다. 꼭 해주자.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileUrl", ignore = true)
    @Mapping(target = "userState", expression = "java(com.core.api_core.user.model.UserState.ACTIVE)")
    User toEntity(UserSignupRequest dto);

    UserInformationResponse toDto(User user);

    UserInformationByAdminResponse toAdminResponse(User user);

    UserProfileResponse toProfile(User user);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profileUrl", ignore = true)
    @Mapping(target = "brith", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "userState", ignore = true)
    @Mapping(target = "nationality", ignore = true)
    void updateUser(UserProfileUpdateRequest userProfileUpdateRequest, @MappingTarget User user);


}
