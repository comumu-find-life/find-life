package com.service.chat;

import com.service.ServiceApplication;
import com.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = ServiceApplication.class)
@ActiveProfiles("test")
public class DirectMessageRoomServiceTest {

    @Test
    void 채팅방_목록_조회_테스트(){

    }
}
