package com.api.directmessage;

import com.api.config.TestConfig;
import com.common.chat.response.DirectMessageRoomListResponse;
import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.repository.DirectMessageRoomRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.chat_core.chat.repository.DirectMessageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.chat.DirectMessageRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.api.config.ApiUrlConstants.DM_FIND_ALL_ROOMS;
import static com.core.directmessage.DirectMessageBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DirectMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private DirectMessageRoomRepository directMessageRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DirectMessageRoomService directMessageRoomService;

    private String token;


    @BeforeEach
    public void setUp() {
        token = "Bearer your-jwt-token";
    }

    @Test
    void 자신의_모든_채팅목록_조회_테스트() throws Exception {
        //given
        User getter = userRepository.save(createGetter("123123"));
        User provider = userRepository.save(createProvider("123123"));

        DirectMessageRoom save = directMessageRoomRepository.save(createDirectMessageRoom(getter, provider));

        //when
        ResultActions resultActions = mockMvc.perform(get(DM_FIND_ALL_ROOMS,1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));


        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        JsonNode dataNode = root.path("data");
        List<DirectMessageRoomListResponse> responses = objectMapper.convertValue(dataNode, new TypeReference<>(){});

  //      then
    }
}
