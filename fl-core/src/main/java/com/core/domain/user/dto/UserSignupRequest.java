package com.core.domain.user.dto;

import com.core.domain.user.model.Gender;
import com.core.domain.user.model.SignupType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupRequest {
    private String fcmToken;

    private String email;

    private String nickname;

    private String password;

    private String job;

    private  Integer brith;

    private  Integer phoneNumber;

    private Gender gender;

    private  String nationality;

    private String introduce;

    private SignupType signupType;

}
