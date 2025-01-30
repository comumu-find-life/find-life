package com.api.auth.constants;

public class AuthConstants {
    public static final String SUCCESS_GENERATED_TOKEN = "토큰 발급 성공";
    public static final String FAIL_TO_TRANSFER_TOKEN ="토큰 전송 실패";
    public static final String NOT_VALID_TOKEN = "유효하지 않은 토큰입니다.";
    public static final String FAIL_TO_LOGIN = "로그인에 실패했습니다.";
    public static final String NOT_EXIST_EMAIL = "해당 이메일이 존재하지 않습니다.";
    public static final String NOT_EXIST_REFRESH_TOKEN = "Refresh 토큰이 존재하지 않습니다.";
    public static final String NOT_MATCH_PASSWORD = "비밀번호가 일치하지 않습니다.";
    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    public static final String EMAIL_CLAIM = "email";
    public static final String BEARER = "Bearer ";
    public static final String ROLE = "Role";
}
