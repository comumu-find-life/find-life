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

    private Role role;

    private String nickName;

    private String password;

    private String job;

    private  Integer brith;

    private  Integer phoneNumber;

    private Gender gender;

    private  String nationality;



}
