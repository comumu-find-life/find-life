package com.common.user.mapper;

import com.common.user.request.UserAccountRequest;
import com.common.user.request.UserProfileUpdateRequest;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.*;
import com.core.api_core.user.model.PointHistory;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (componentMode = 'spring') 을 안하면 빈 등록이 안된다. 꼭 해주자.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileUrl", ignore = true)
//    @Mapping(target = "userAccount", ignore = true)
    @Mapping(target = "userState", expression = "java(com.core.api_core.user.model.UserState.ACTIVE)")
    User toEntity(UserSignupRequest dto);

    UserInformationResponse toDto(User user);

    UserInformationByAdminResponse toAdminResponse(User user);

    UserProfileResponse toProfile(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "point", expression = "java(0)")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "chargeHistories", ignore = true)
    UserAccount toUserAccount(UserAccountRequest userAccountRequest, Long userId);

    @Mapping(target = "chargeHistories", source = "userAccount.chargeHistories", qualifiedByName = "mapChargeHistories")
    UserAccountResponse toUserAccountResponse(UserAccount userAccount);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profileUrl", ignore = true)
    @Mapping(target = "brith", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "userState", ignore = true)
    @Mapping(target = "nationality", ignore = true)
    @Mapping(target = "signupType", ignore = true)
    void updateUser(UserProfileUpdateRequest userProfileUpdateRequest, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "chargeHistories", ignore = true)
    void updateUserAccount(UserAccountRequest userAccountRequest, @MappingTarget UserAccount userAccount);

    @Named("mapChargeHistories")
    default List<PointChargeHistoryResponse> mapImageUrls(List<PointHistory> pointChargeHistories) {
        return pointChargeHistories.stream().map(pointChargeHistory -> {
            return PointChargeHistoryResponse.builder()
                    .chargeAmount(pointChargeHistory.getChargeAmount())
                    .chargeType(pointChargeHistory.getChargeType())
                    .chargeDate(pointChargeHistory.getHistoryDateTime())
                    .build();
        }).collect(Collectors.toList());

    }
}
