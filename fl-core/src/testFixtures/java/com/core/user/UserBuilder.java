package com.core.user;

import com.core.domain.user.model.Gender;
import com.core.domain.user.model.Role;
import com.core.domain.user.model.User;

public class UserBuilder {
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

    public static User createUser(String encodePassword) {
        return User.builder()
                .id(5L)
                .email(USER_EMAIL)
                .nickname(USER_NICKNAME) //
                .password(encodePassword) //
                .job(USER_JOB)
                .profileUrl(USER_PROFILE_URL)
                .brith(USER_PHONE_BRITH)
                .phoneNumber(USER_PHONE_NUMBER)
                .gender(USER_GENDER)
                .nationality(USER_NATIONALITY)
                .build();
    }
}
