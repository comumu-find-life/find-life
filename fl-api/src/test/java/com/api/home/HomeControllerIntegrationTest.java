package com.api.home;
import com.api.dto.SuccessResponse;
import com.api.security.service.JwtService;
import com.core.home.model.*;
import com.core.home.reposiotry.HomeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.LocationService;
import com.service.home.dto.request.HomeGeneratorRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.config.HomeGenerator.generateHomeEntity;
import static com.api.config.HomeGenerator.generateHomeGeneratorRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeRepository repository;

    @Autowired
    private LocationService locationService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void setUp() {
        // JWT 토큰을 생성하여 token 변수에 저장합니다.
        // 실제 JWT 생성 로직은 구현에 따라 달라질 수 있습니다.
        token = "Bearer your-jwt-token";
        repository.save(generateHomeEntity());
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_게시글_생성_테스트() throws Exception {
        // given
        HomeGeneratorRequest homeGeneratorRequest = generateHomeGeneratorRequest();

        // when
        mockMvc.perform(post("/v1/api/home")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(homeGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, "집 게시글 등록 성공", 2L))));

        // then
        Home home = repository.findById(2L).get();
        Assertions.assertThat(home.getUserId()).isEqualTo(homeGeneratorRequest.getUserId());
        Assertions.assertThat(home.getBathRoomCount()).isEqualTo(homeGeneratorRequest.getBathRoomCount());
        Assertions.assertThat(home.getBedroomCount()).isEqualTo(homeGeneratorRequest.getBedroomCount());
        Assertions.assertThat(home.getBond()).isEqualTo(homeGeneratorRequest.getBond());
        Assertions.assertThat(home.getGender()).isEqualTo(homeGeneratorRequest.getGender());
        Assertions.assertThat(home.getType()).isEqualTo(homeGeneratorRequest.getType());
        Assertions.assertThat(home.getIntroduce()).isEqualTo(homeGeneratorRequest.getIntroduce());
        Assertions.assertThat(home.getBill()).isEqualTo(homeGeneratorRequest.getBill());
        Assertions.assertThat(home.getRent()).isEqualTo(homeGeneratorRequest.getRent());
        Assertions.assertThat(home.getStatus()).isEqualTo(HomeStatus.FOR_SALE);
    }


    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_상태_변경_테스트() throws Exception {
        //given
        Long homeId = 1L;

        //when
        mockMvc.perform(post("/v1/api/home/sell")
                        .param("homeId", homeId.toString())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, "집 판매 완료", null))));

        //then
        Home home = repository.findById(homeId).get();
        Assertions.assertThat(home.getStatus()).isEqualTo(HomeStatus.SOLD_OUT);
    }


}
