package com.api.user;

import com.common.user.request.UserProfileUpdateRequest;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserProfileResponse;
import com.common.utils.SuccessResponse;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.LocationService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.api.config.ApiUrlConstants.USERS_IMAGE_UPDATE;
import static com.api.config.ApiUrlConstants.USERS_UPDATE;
import static com.core.user.UserBuilder.USER_EMAIL;
import static com.core.user.UserBuilder.createUser;
import static com.core.user.request.UserRequestBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocationService locationService;

    @Autowired
    private EntityManager entityManager;

    private String token;

    @BeforeEach
    void setInit() {
        token = "Bearer your-jwt-token";
        userRepository.deleteAll();
    }

    @Test
    void 회원가입_테스트() throws Exception {
        //given
        UserSignupRequest request = createSignupDto();
        String requestJson = objectMapper.writeValueAsString(request);

        // MockMultipartFile을 사용하여 테스트용 파일 생성
        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        MockMultipartFile jsonFile = new MockMultipartFile(
                "userSignupRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                requestJson.getBytes()
        );

        //when
        mockMvc.perform(multipart("/v1/api/users/sign-up")
                        .file(mockFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        User user = userRepository.findByEmail(USER_SIGNUP_EMAIL).orElse(null);

        //then
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getNickname()).isEqualTo(USER_NICKNAME);
    }


    @Test
    void 상대방_프로필_조회_테스트() throws Exception {
        // given
        User user = createUser(passwordEncoder.encode("123123"));
        User savedUser = userRepository.save(user);

        // when
        MvcResult result = mockMvc.perform(get("/v1/api/users/profile/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        SuccessResponse response = objectMapper.readValue(responseBody, SuccessResponse.class);
        UserProfileResponse userProfileResponse = objectMapper.convertValue(response.getData(), UserProfileResponse.class);

        // then
        Assertions.assertThat(response.isSuccess()).isTrue();
        Assertions.assertThat(userProfileResponse.getId()).isEqualTo(savedUser.getId());
        Assertions.assertThat(userProfileResponse.getNickname()).isEqualTo(savedUser.getNickname());
    }

    @Test
    void 사용자_프로필_정보_수정_테스트() throws Exception {
        // given
        User user = createUser(passwordEncoder.encode("123123"));
        UserProfileUpdateRequest userProfileUpdateRequest = createUserProfileUpdateRequest();
        User save = userRepository.save(user);

        //when
        mockMvc.perform(patch(USERS_UPDATE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfileUpdateRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        //then
        User updateUser = userRepository.findById(save.getId()).get();

        Assertions.assertThat(updateUser.getNickname()).isEqualTo("newNickname");
        Assertions.assertThat(updateUser.getIntroduce()).isEqualTo("newIntroduce");
        Assertions.assertThat(updateUser.getJob()).isEqualTo("newJob");
    }

    @Test
    void 사용자_프로필_이미지_수정_테스트() throws Exception {
        // given
        User user = createUser(passwordEncoder.encode("123123"));
        User save = userRepository.save(user);
        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "new-profile.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        //when
        mockMvc.perform(multipart(USERS_IMAGE_UPDATE, save.getId())
                        .file(mockFile)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        }))
                .andExpect(status().isOk());

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        //then
        User updateUser = userRepository.findById(save.getId()).get();

        Assertions.assertThat(user.getProfileUrl()).isNotEqualTo(updateUser.getProfileUrl());
    }
}
