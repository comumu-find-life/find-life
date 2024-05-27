package com.service.user.mapper;

import com.core.user.model.User;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserProfileRequest;
import com.service.user.dto.UserSignupRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-26T22:39:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserSignupRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.role( dto.getRole() );
        user.nickName( dto.getNickName() );
        user.password( dto.getPassword() );
        user.profileUrl( dto.getProfileUrl() );
        user.brith( dto.getBrith() );
        user.phoneNumber( dto.getPhoneNumber() );
        user.job( dto.getJob() );
        user.gender( dto.getGender() );
        user.nationality( dto.getNationality() );

        return user.build();
    }

    @Override
    public UserInformationDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserInformationDto.UserInformationDtoBuilder userInformationDto = UserInformationDto.builder();

        userInformationDto.id( user.getId() );
        userInformationDto.nickName( user.getNickName() );
        userInformationDto.profileUrl( user.getProfileUrl() );

        return userInformationDto.build();
    }

    @Override
    public UserProfileRequest toProfile(User user) {
        if ( user == null ) {
            return null;
        }

        UserProfileRequest.UserProfileRequestBuilder userProfileRequest = UserProfileRequest.builder();

        userProfileRequest.nickName( user.getNickName() );
        userProfileRequest.profileUrl( user.getProfileUrl() );
        userProfileRequest.job( user.getJob() );
        userProfileRequest.gender( user.getGender() );
        userProfileRequest.nationality( user.getNationality() );

        return userProfileRequest.build();
    }
}
