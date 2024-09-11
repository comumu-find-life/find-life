package com.core.user.request;

import com.common.user.request.UserSignupRequest;
import com.core.api_core.user.model.Gender;
import com.core.api_core.user.model.Role;

public class UserRequestBuilder {

    public static final String USER_SIGNUP_EMAIL = "sin1768@naver.com";
    public static final String USER_NICKNAME = "minseok";
    public static final Role USER_ROLE = Role.GETTER;
    public static final String USER_PASSWORD = "sin981023@";
    public static final String USER_JOB = "student";

    public static UserSignupRequest createSignupDto(){
        return UserSignupRequest.builder()
                .email(USER_SIGNUP_EMAIL)
                .nickname(USER_NICKNAME)
                .password(USER_PASSWORD)
                .job(USER_JOB)
                .role(USER_ROLE)
                .brith(25)
                .phoneNumber(01012341234)
                .gender(Gender.MALE)
                .nationality("Korean")
                .build();
    }
}
