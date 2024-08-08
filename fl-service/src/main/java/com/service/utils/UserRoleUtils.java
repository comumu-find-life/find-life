package com.service.utils;

public class UserRoleUtils {

    public static boolean isProvider(Long userId, Long homeId){
        return userId == homeId;
    }
}
