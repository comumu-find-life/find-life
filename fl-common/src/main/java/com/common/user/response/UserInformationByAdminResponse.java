package com.common.user.response;

import com.core.api_core.user.model.Gender;
import com.core.api_core.user.model.SignupType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInformationByAdminResponse {

    private Long id;

    private String email;

    private String nickName;

    private String profileUrl;

    private Integer brith;

    private Integer phoneNumber;

    private String job;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SignupType signupType;

    private String nationality;
    //자기 소개글
    private String introduce;


}
