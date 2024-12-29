//package com.user;
//
//import com.admin.AdminApplication;
//import com.common.user.request.UserSignupRequest;
//import com.common.user.response.UserInformationResponse;
//import com.core.admin_core.user.repository.AdminUserRepository;
//import com.core.api_core.user.model.User;
//import com.core.api_core.user.model.UserState;
//import com.core.api_core.user.repository.UserRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.service.home.LocationService;
//import jakarta.persistence.EntityManager;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static com.admin.config.AdminApiUrls.*;
//import static com.core.user.UserBuilder.createUser;
//import static com.core.user.request.UserRequestBuilder.createSignupDto;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = AdminApplication.class)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//public class AdminUserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private AdminUserRepository adminUserRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private LocationService locationService;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    private String token;
//
//    @BeforeEach
//    void setInit() {
//        token = "Bearer your-jwt-token";
//    }
//
//    @Test
//    void 관리자_회원가입_테스트() throws Exception {
//        //given
//        UserSignupRequest request = createSignupDto();
//        String requestJson = objectMapper.writeValueAsString(request);
//
//        //when
//        mockMvc.perform(post(USER_SIGN_UP)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        //then
//        adminUserRepository.findByEmail(request.getEmail()).isPresent();
//    }
//
//    @Test
//    void 모든_회원_조회_테스트() throws Exception {
//        //given
//        userRepository.save(createUser(passwordEncoder.encode("123123")));
//
//        //when
//        ResultActions result = mockMvc.perform(get(USERS)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        String responseString = result.andReturn().getResponse().getContentAsString();
//        JsonNode root = objectMapper.readTree(responseString);
//        JsonNode dataNode = root.path("data");
//        List<UserInformationResponse> responses = objectMapper.convertValue(dataNode, new TypeReference<>() {
//        });
//
//        Assertions.assertThat(responses.size()).isEqualTo(1);
//    }
//
//    @Test
//    void 회원_비_활성화_테스트() throws Exception {
//        //given
//        User save = userRepository.save(createUser(passwordEncoder.encode("123123")));
//
//        //when
//        mockMvc.perform(patch(USER_INACTIVE, save.getId())
//                        .header(HttpHeaders.AUTHORIZATION, token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        // 영속성 컨텍스트 초기화
//        entityManager.flush();
//        entityManager.clear();
//
//
//        //then
//        Assertions.assertThat(save.getUserState()).isEqualTo(UserState.INACTIVE);
//    }
//
//}
