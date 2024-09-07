package com.api.home;
import com.api.config.TestConfig;
import com.api.security.service.JwtService;
import com.common.home.request.HomeAddressGeneratorRequest;
import com.common.home.request.HomeGeneratorRequest;
import com.common.home.request.HomeUpdateRequest;
import com.common.utils.SuccessResponse;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.utils.LatLng;
import com.service.home.impl.LocationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.config.ApiUrlConstants.HOMES_BASE_URL;
import static com.api.config.ApiUrlConstants.HOMES_VALIDATE_ADDRESS;
import static com.api.helper.HomeHelper.*;
import static com.api.helper.UserHelper.generateUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class HomeControllerIntegrationTest {

    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationServiceImpl locationService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer your-jwt-token";
        userRepository.save(generateUser(passwordEncoder));
        repository.save(generateHomeEntity());
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_게시글_생성_테스트() throws Exception {
        // given
        HomeGeneratorRequest homeGeneratorRequest = generateHomeGeneratorRequest();
        MockMultipartFile jsonFile = new MockMultipartFile("homeGeneratorRequest", "", "application/json",
                objectMapper.writeValueAsBytes(homeGeneratorRequest));
        MockMultipartFile image1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "image2".getBytes());

        // when
        mockMvc.perform(MockMvcRequestBuilders.multipart(HOMES_BASE_URL)
                        .file(jsonFile)
                        .file(image1)
                        .file(image2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Assertions.assertThat(repository.findAll().size()).isEqualTo(11);
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_게시글_수정_테스트() throws Exception {
        Home home1 = repository.findById(1L).get();
        System.out.println("Before Address : " + home1.getHomeAddress().getState() + home1.getHomeAddress().getCity());
        // given
        HomeUpdateRequest homeGeneratorRequest = generateHomeUpdateRequest();
        System.out.println("To Change Address : " + homeGeneratorRequest.getHomeAddress().getState() + homeGeneratorRequest.getHomeAddress().getCity());
        // when
        mockMvc.perform(patch(HOMES_BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(homeGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, "집 게시글 수정 성공", null))));

        // then
        Home home = repository.findById(1L).get();
        System.out.println("After Address : " + home.getHomeAddress().getState() + home.getHomeAddress().getCity());
        Assertions.assertThat(home.getHomeInfo().getBond()).isEqualTo(homeGeneratorRequest.getBond());
        Assertions.assertThat(home.getHomeAddress().getState()).isEqualTo(homeGeneratorRequest.getHomeAddress().getState());
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 주소_유효성_검사_테스트() throws Exception {
        // given
        HomeAddressGeneratorRequest homeAddressGeneratorRequest = generateHomeAddressGeneratorReqeust();

        LatLng expectedLatLng = new LatLng(-33.8689919, 151.2080409);  // 예상되는 위도와 경도
        SuccessResponse expectedResponse = new SuccessResponse(true, SuccessHomeMessages.ADDRESS_VALIDATION_SUCCESS, expectedLatLng);

        mockMvc.perform(post(HOMES_VALIDATE_ADDRESS)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(homeAddressGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }


}
