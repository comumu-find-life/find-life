package com.api.deal;

import com.api.home.SuccessHomeMessages;
import com.api.security.service.JwtService;
import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealResponse;
import com.common.home.request.HomeAddressGeneratorRequest;
import com.common.home.request.HomeGeneratorRequest;
import com.common.home.request.HomeUpdateRequest;
import com.common.utils.SuccessResponse;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.reposiotry.HomeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.utils.LatLng;
import com.service.home.impl.LocationServiceImpl;
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

import static com.api.deal.SuccessProtectedDealMessages.DEAL_CREATED;
import static com.api.helper.HomeHelper.*;
import static com.api.helper.ProtectedDealHelper.generateProtectedDealFindRequest;
import static com.api.helper.ProtectedDealHelper.generateProtectedDealGeneratorRequest;
import static com.api.home.SuccessHomeMessages.USER_POSTS_RETRIEVE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProtectedDealIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProtectedDealRepository repository;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer your-jwt-token";
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 안전거래_생성_테스트() throws Exception {
        //given
        ProtectedDealGeneratorRequest protectedDealGeneratorRequest = generateProtectedDealGeneratorRequest();
        SuccessResponse expectedResponse = new SuccessResponse(true, DEAL_CREATED, null);
        //when

        mockMvc.perform(post("/v1/api/deals")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(protectedDealGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
        //then
        Assertions.assertThat(repository.findAll().size()).isEqualTo(5);
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 안전거래_단일_조회_테스트() throws Exception {
        //given
        ProtectedDealFindRequest protectedDealFindRequest = generateProtectedDealFindRequest();
        //when
        ResultActions resultActions = mockMvc.perform(post("/v1/api/deals/read")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(protectedDealFindRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        //then
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        JsonNode dataNode = root.path("data");
        ProtectedDealResponse protectedDealResponse = objectMapper.treeToValue(dataNode, ProtectedDealResponse.class);
        Assertions.assertThat(protectedDealResponse.getAccount()).isEqualTo("123-456-789");
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 자신의_안전거래_조회_테스트() throws Exception {

    }
}
