package com.common.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInformationDto {

    private Long id;

    private String email;

    private String nickname;

    private String profileUrl;

}
