package com.api.directmessage;

import com.api.ApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ApiApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Test
    void das(){
        System.out.println("DASD");
    }
}
