package com.redis.utils;

import java.util.Random;

public class EmailCodeGenerator {
    private static final int CODE_LENGTH = 6;  // 인증 코드의 길이 설정

    public static String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));  // 0~9까지의 숫자를 랜덤하게 추가
        }

        return code.toString();
    }
}
