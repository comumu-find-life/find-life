package com.service.user;

import com.service.user.dto.UserSignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception {
        UserSignupRequest build = UserSignupRequest.builder()
                .email("testemail@naver.com")
                .password("passwrod")
                .nickName("닉네임")
                .build();
        userService.signUp(build);

    }
}
