package com.api.helper;

import com.common.user.request.UserSignupRequest;
import com.core.api_core.user.model.Gender;
import com.core.api_core.user.model.Role;
import com.core.api_core.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserHelper {

    public static final String USER_EMAIL = "user";
    public static final String USER_SIGNUP_EMAIL = "sin1768@naver.com";
    public static final String USER_NICKNAME = "minseok";
    public static final Role USER_ROLE = Role.GETTER;
    public static final String USER_PASSWORD = "sin981023@";
    public static final String USER_JOB = "student";
    public static final Gender USER_GENDER = Gender.MALE;
    public static final Integer USER_PHONE_NUMBER = 01012341234;
    public static final Integer USER_PHONE_BRITH = 25;
    public static final String USER_PROFILE_URL = "url";
    public static final String USER_NATIONALITY = "Korean";


    public static User generateUser(PasswordEncoder passwordEncoder){
        return User.builder()
                .id(1L)
                .email(USER_EMAIL)
                .nickname(USER_NICKNAME) //
                .role(USER_ROLE)
                .password(passwordEncoder.encode(USER_PASSWORD)) //
                .job(USER_JOB)
                .profileUrl(USER_PROFILE_URL)
                .brith(USER_PHONE_BRITH)
                .phoneNumber(USER_PHONE_NUMBER)
                .gender(USER_GENDER)
                .nationality(USER_NATIONALITY)
                .build();
    }

    public static UserSignupRequest generateSignupDto(){
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
