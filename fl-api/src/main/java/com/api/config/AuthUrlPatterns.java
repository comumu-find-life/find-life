package com.api.config;


public final class AuthUrlPatterns {
    private static final String ROOT_URL = "/v1/api";

    public static final String[] GET_AUTH_WHITELIST = {
            ROOT_URL + "/homes",
            ROOT_URL + "/homes/**",
            ROOT_URL + "/homes/city",
            ROOT_URL + "/homes/{homeId}",
            ROOT_URL + "/homes/users/{userId}",
            ROOT_URL + "/dm",
            ROOT_URL + "/dm/**",
            ROOT_URL + "/homes/overview",
            ROOT_URL + "/users/profile/**",
    };

    public static final String[] POST_AUTH_WHITELIST = {
            ROOT_URL + "/users/profile/{userId}",
            ROOT_URL + "/users/sign-up",
            ROOT_URL + "/users/login",
            ROOT_URL + "/users/verification/email/send/**"

    };
}