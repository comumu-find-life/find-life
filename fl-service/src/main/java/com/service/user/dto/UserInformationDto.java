package com.service.user.dto;

import lombok.Builder;

@Builder
public class UserInformationDto {

    private Long id;

    private String nickName;

    private String profileUrl;
}
