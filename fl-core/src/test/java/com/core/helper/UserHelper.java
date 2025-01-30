package com.core.helper;

import com.core.domain.user.model.User;

public class UserHelper {

    public static User generateUser(Long id){
        return User.builder()
                .id(id)
                .password("123123")
                .nickname("user"+id)
                .build();
    }
}
