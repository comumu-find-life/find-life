package com.api.home;
import com.api.config.TestConfig;
import com.common.home.request.HomeAddressGeneratorRequest;
import com.common.home.request.HomeGeneratorRequest;
import com.common.home.request.HomeUpdateRequest;
import com.common.home.response.HomeOverviewResponse;
import com.common.utils.SuccessResponse;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.utils.LatLng;
import com.service.home.impl.LocationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.config.ApiUrlConstants.*;
import static com.api.helper.UserHelper.generateUser;
import static com.core.home.HomeBuilder.createHome;
import static com.core.home.request.HomeRequestBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer your-jwt-token";
        userRepository.save(generateUser(passwordEncoder));
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_게시글_생성_테스트() throws Exception {
        // given
        HomeGeneratorRequest homeGeneratorRequest = createHomeGeneratorRequest();
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

        Home home = repository.findById(1L).get();



        Assertions.assertThat(home.getHomeStatus()).isEqualTo(HomeStatus.FOR_SALE);
        Assertions.assertThat(home.getHomeInfo().getBond()).isEqualTo(homeGeneratorRequest.getBond());
        Assertions.assertThat(home.getHomeInfo().getIntroduce()).isEqualTo(homeGeneratorRequest.getIntroduce());
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_게시글_수정_테스트() throws Exception {
        // given
        Home save = repository.save(createHome());
        HomeUpdateRequest homeGeneratorRequest = createHomeUpdateRequest(save.getId());

        // when
        mockMvc.perform(patch(HOMES_BASE_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(homeGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, "집 게시글 수정 성공", null))));

        // then
        Home home = repository.findById(save.getId()).get();
        Assertions.assertThat(home.getHomeAddress().getState()).isEqualTo(homeGeneratorRequest.getHomeAddress().getState());
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_주소_검색_테스트() throws Exception {
        // given
        HomeAddressGeneratorRequest homeAddressGeneratorRequest = createHomeAddressGeneratorRequest();

        LatLng expectedLatLng = new LatLng(-33.8708464, 151.20733);  // 예상되는 위도와 경도
        SuccessResponse expectedResponse = new SuccessResponse(true, SuccessHomeMessages.ADDRESS_VALIDATION_SUCCESS, expectedLatLng);

        mockMvc.perform(post(HOMES_VALIDATE_ADDRESS)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(homeAddressGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void 사용자_모든_집_게시글_조회_테스트() throws Exception {
        //given
        Long userID = userRepository.findAll().get(0).getId();

        repository.save(createHome());
        repository.save(createHome());
        repository.save(createHome());


        ResultActions resultActions =  mockMvc.perform(get(HOMES_FIND_BY_USER_ID, userID)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        //then
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        JsonNode dataNode = root.path("data");
        List<HomeOverviewResponse> responses = objectMapper.convertValue(dataNode, new TypeReference<>(){});
        System.out.println("DASDASD");
        System.out.println(responses.size());

//        Assertions.assertThat(responses.size()).isEqualTo(3);
    }

}
