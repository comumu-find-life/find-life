package com.core.user.request;

import com.core.domain.user.dto.UserProfileUpdateRequest;
import com.core.domain.user.dto.UserSignupRequest;
import com.core.domain.user.model.Gender;
import com.core.domain.user.model.Role;

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
                .brith(25)
                .phoneNumber(01012341234)
                .gender(Gender.MALE)
                .nationality("Korean")
                .build();
    }

    public static UserProfileUpdateRequest createUserProfileUpdateRequest(){
        return UserProfileUpdateRequest.builder()
                .nickname("newNickname")
                .introduce("newIntroduce")
                .gender(Gender.FEMALE)
                .userId(1L)
                .job("newJob")
                .build();
    }
}
