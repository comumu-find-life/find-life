package com.deal;

import com.admin.AdminApplication;
import com.common.deal.response.ProtectedDealAdminResponse;
import com.common.deal.response.ProtectedDealOverViewResponse;
import com.common.user.response.UserInformationResponse;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.admin.config.AdminApiUrls.*;
import static com.core.deal.ProtectedDealBuilder.createProtectedDealByDealState;
import static com.core.home.HomeBuilder.createHome;
import static com.core.user.UserBuilder.createUser;
import static com.utils.TestUtils.extractDataFromResponse;
import static com.utils.TestUtils.extractDataListFromResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdminApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProtectedDealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProtectedDealRepository protectedDealRepository;

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private String token;

    @BeforeEach
    void setInit() {
        token = "Bearer your-jwt-token";
    }

    @Test
    void 입금_신청된_안전거래_조회_테스트() throws Exception {
        //given
        Home home = homeRepository.save(createHome());
        protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));
        protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));
        protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));

        //when
        ResultActions result = mockMvc.perform(get(DEALS_REQUEST_DEPOSIT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<ProtectedDealOverViewResponse> responses = extractDataListFromResponse(result, new TypeReference<>() {});

        Assertions.assertThat(responses.size()).isEqualTo(3);
    }

    @Test
    void 안전거래_단일조회_테스트() throws Exception {
        //given
        userRepository.save(createUser("password"));
        userRepository.save(createUser("password"));
        Home home = homeRepository.save(createHome());
        ProtectedDeal protectedDeal = protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));

        //when
        ResultActions result = mockMvc.perform(get(DEAL_BY_ID, protectedDeal.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        //then
        ProtectedDealAdminResponse response = extractDataFromResponse(result, ProtectedDealAdminResponse.class);

        Assertions.assertThat(response.getDeposit()).isEqualTo(2000);
    }

    @Test
    void 입금_완료_테스트() throws Exception {
        //given
        Home home = homeRepository.save(createHome());
        ProtectedDeal protectedDeal = protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));

        //when
        mockMvc.perform(patch(DEAL_CONFIRM_DEPOSIT, protectedDeal.getId())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        //then
        ProtectedDeal updateDeal = protectedDealRepository.findById(protectedDeal.getId()).get();
        Assertions.assertThat(updateDeal.getDealState()).isEqualTo(DealState.COMPLETE_DEPOSIT);
    }

    @Test
    void 입금_신청_취소_테스트() throws Exception {
        //given
        Home home = homeRepository.save(createHome());
        ProtectedDeal protectedDeal = protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));

        //when
        mockMvc.perform(patch(DEAL_CANCEL_DEPOSIT, protectedDeal.getId())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        //then
        ProtectedDeal updateDeal = protectedDealRepository.findById(protectedDeal.getId()).get();
        Assertions.assertThat(updateDeal.getDealState()).isEqualTo(DealState.CANCEL_DEPOSIT);
    }

    @Test
    void 거래_완료_신청된_안전거래_조회_테스트() throws Exception {
        //given
        Home home = homeRepository.save(createHome());
        protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_COMPLETE_DEAL));
        protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_COMPLETE_DEAL));
        protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));

        //when
        ResultActions result = mockMvc.perform(get(DEAL_SUBMIT_COMPLETED)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<ProtectedDealOverViewResponse> responses = extractDataListFromResponse(result, new TypeReference<>() {});

        Assertions.assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    void 거래_완료_테스트() throws Exception {
        //given
        Home home = homeRepository.save(createHome());
        ProtectedDeal protectedDeal = protectedDealRepository.save(createProtectedDealByDealState(home.getId(), DealState.REQUEST_DEPOSIT));

        //when
        mockMvc.perform(patch(DEAL_COMPLETE, protectedDeal.getId())
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        //then
        ProtectedDeal updateDeal = protectedDealRepository.findById(protectedDeal.getId()).get();
        Assertions.assertThat(updateDeal.getDealState()).isEqualTo(DealState.COMPLETE_DEAL);
    }


}
