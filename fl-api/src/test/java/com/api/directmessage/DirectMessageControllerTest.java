package com.api.directmessage;

import com.api.security.service.JwtService;
import com.common.deal.response.ProtectedDealByProviderResponse;
import com.core.api_core.chat.repository.DirectMessageRoomRepository;
import com.core.chat_core.chat.repository.DirectMessageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.api.config.ApiUrlConstants.DM_FIND_ALL_ROOMS;
import static com.api.helper.DirectMessageRoomHelper.generateDirectMessage;
import static com.api.helper.DirectMessageRoomHelper.generateDirectMessageRoom;
import static com.api.helper.HomeHelper.generateHomeEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DirectMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private DirectMessageRoomRepository directMessageRoomRepository;

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
    void 자신의_모든_채팅목록_조회_테스트() throws Exception {
//        given
        directMessageRoomRepository.save(generateDirectMessageRoom());
        directMessageRepository.save(generateDirectMessage());

        //when
        ResultActions resultActions = mockMvc.perform(get(DM_FIND_ALL_ROOMS,1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

  //      then
    }
}
