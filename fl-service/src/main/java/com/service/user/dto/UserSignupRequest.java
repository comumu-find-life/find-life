package com.service.user.dto;

import com.core.user.model.Gender;
import com.core.user.model.Role;
import com.core.user.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupRequest {
    private String email;

    private String password;

    private String nickName;

    private  Integer phoneNumber;

    private  String profileUrl;

    private  Integer age;

    private Gender gender;

    private  String nationality;

    private Role role;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .nationality(nationality)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }
}
