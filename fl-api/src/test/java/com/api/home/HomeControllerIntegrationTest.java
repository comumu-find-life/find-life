package com.api.home;
import com.api.dto.SuccessResponse;
import com.api.security.service.JwtService;
import com.core.home.model.*;
import com.core.home.reposiotry.HomeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.dto.request.HomeUpdateRequest;
import com.service.home.impl.LocationServiceImpl;
import com.service.home.dto.request.HomeGeneratorRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.config.HomeGenerator.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeRepository repository;

    @Autowired
    private LocationServiceImpl locationService;

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
        MockMultipartFile jsonFile = new MockMultipartFile("homeCreateDto", "", "application/json",
                objectMapper.writeValueAsBytes(homeGeneratorRequest));
        MockMultipartFile image1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image1".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "image2".getBytes());

        // when
        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/api/home")
                        .file(jsonFile)
                        .file(image1)
                        .file(image2)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, "집 게시글 등록 성공", 2L))));

        // then
        Home home = repository.findById(2L).get();
        Assertions.assertThat(home.getUserId()).isEqualTo(homeGeneratorRequest.getUserId());
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 집_게시글_수정_테스트() throws Exception {
        // given
        HomeUpdateRequest homeGeneratorRequest = generateHomeUpdateRequest();

        // when
        mockMvc.perform(patch("/v1/api/home")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(homeGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(new SuccessResponse(true, "집 게시글 수정 성공", null))));
//
//        // then
        Home home = repository.findById(1L).get();


        Assertions.assertThat(home.getBathRoomCount()).isEqualTo(homeGeneratorRequest.getBathRoomCount());
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
