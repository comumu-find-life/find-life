package com.api.user;

import com.core.user.model.Gender;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.user.dto.UserSignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 회원가입_테스트() throws Exception {
        UserSignupRequest request =  UserSignupRequest.builder()
                .email("test1234@naver.com")
                .nickName("민석")
                .password("sin981023@")
                .job("student")
                .profileUrl("url")
                .brith(25)
                .phoneNumber(01012341234)
                .gender(Gender.MALE)
                .nationality("Korean")
                .build();

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/v1/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        User user = userRepository.findByEmail("test1234@naver.com").orElse(null);

        Assertions.assertThat(user.getNickName()).isEqualTo("민석");
    }
}
