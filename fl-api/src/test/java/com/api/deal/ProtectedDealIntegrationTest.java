package com.api.deal;

import com.api.security.service.JwtService;
import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealResponse;
import com.common.utils.SuccessResponse;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.utils.OptionalUtil;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.api.config.ApiUrlConstants.*;
import static com.api.deal.SuccessProtectedDealMessages.DEAL_CREATED;
import static com.api.helper.ProtectedDealHelper.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
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
        repository.deleteAll(); // 모든 데이터를 삭제
        token = "Bearer your-jwt-token";
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 안전거래_생성_테스트() throws Exception {
        //given
        ProtectedDealGeneratorRequest protectedDealGeneratorRequest = generateProtectedDealGeneratorRequest();
        SuccessResponse expectedResponse = new SuccessResponse(true, DEAL_CREATED, null);
        //when

        mockMvc.perform(post(DEALS_SAVE)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(protectedDealGeneratorRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        //then
        Assertions.assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 안전거래_단일_조회_테스트() throws Exception {
        //given
        repository.save(generateProtectedDeal());
        ProtectedDealFindRequest protectedDealFindRequest = generateProtectedDealFindRequest();
        //when
        ResultActions resultActions = mockMvc.perform(post(DEALS_READ)
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
        Assertions.assertThat(protectedDealResponse.getAccount()).isEqualTo("account");
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 자신의_안전거래_조회_테스트() throws Exception {
        //given
        repository.save(generateProtectedDeal());

        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/api/deals/" + 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        //then
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        JsonNode dataNode = root.path("data");

        // 응답이 배열 형태이므로 이를 리스트로 변환
        List<ProtectedDealResponse> protectedDealResponseList = objectMapper.convertValue(dataNode, new TypeReference<>(){});

        Assertions.assertThat(protectedDealResponseList.size()).isEqualTo(1);
    }

    /**
     * 이후 상태 변경 API 는 같은 로직이니 테스트 x
     */
    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 입금_신청_테스트() throws Exception {
        //given
        repository.save(generateProtectedDeal());
        List<ProtectedDeal> all = repository.findAll();
        Long id = all.get(0).getId();
        //post
        ResultActions resultActions = mockMvc.perform(post("/v1/api/deals/request/deposit/" + id)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        //then
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(repository.findById(id), "ERROR");
        Assertions.assertThat(protectedDeal.getDealState()).isEqualTo(DealState.DURING_DEPOSIT);
    }

}
