package com.api.user;

import com.api.dto.SuccessResponse;
import com.core.user.model.Gender;
import com.core.user.model.Role;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.user.dto.UserProfileRequest;
import com.service.user.dto.UserSignupRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void 회원가입_테스트() throws Exception {
        //given
        UserSignupRequest request = generateSignupDto("test1234@naver.com");
        String requestJson = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/v1/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        User user = userRepository.findByEmail("test1234@naver.com").orElse(null);

        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getNickName()).isEqualTo("민석");
    }

    @Test
    void 로그인_테스트() throws Exception {
        // given
        User user = generateUser();
        userRepository.save(user);

        // 로그인 요청 JSON 생성
        String loginRequestJson = objectMapper.writeValueAsString(Map.of(
                "email", user.getEmail(),
                "password", "sin981023@"
        ));

        // when
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Map<String, String> responseMap = objectMapper.readValue(responseBody, Map.class);

        // then
        Assertions.assertThat(responseMap.get("accessToken")).isNotBlank();
        Assertions.assertThat(responseMap.get("refreshToken")).isNotBlank();
    }

    @Test
    void 사용자_프로필_조회_테스트() throws Exception {
        // given
        User user = generateUser();
        userRepository.save(user);

        // when
        MvcResult result = mockMvc.perform(get("/v1/api/user/profile")
                        .param("id", String.valueOf(user.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String responseBody = result.getResponse().getContentAsString();
        SuccessResponse response = objectMapper.readValue(responseBody, SuccessResponse.class);
        UserProfileRequest userProfile = objectMapper.convertValue(response.getData(), UserProfileRequest.class);

        // 프로필 정보 검증
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThat(userProfile.getNickName()).isEqualTo(user.getNickName());
        Assertions.assertThat(userProfile.getProfileUrl()).isEqualTo(user.getProfileUrl());
    }

    private UserSignupRequest generateSignupDto(String email){
        return UserSignupRequest.builder()
                .email(email)
                .nickName("민석")
                .password("sin981023@")
                .job("student")
                .role(Role.GETTER)
                .profileUrl("url")
                .brith(25)
                .phoneNumber(01012341234)
                .gender(Gender.MALE)
                .nationality("Korean")
                .build();
    }

    private User generateUser(){
        return User.builder()
                .id(1L)
                .email("sin1768@naver.com")
                .nickName("minseok")
                .role(Role.GETTER)
                .password(passwordEncoder.encode("sin981023@"))// Note: Password should be encoded
                .job("student")
                .profileUrl("url")
                .brith(25)
                .phoneNumber(01012341234)
                .gender(Gender.MALE)
                .nationality("Korean")
                .build();
    }
}
