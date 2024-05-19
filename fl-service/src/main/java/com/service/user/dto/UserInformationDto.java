package com.service.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInformationDto {

    private Long id;

    private String email;

    private String nickName;

    private String profileUrl;
}
