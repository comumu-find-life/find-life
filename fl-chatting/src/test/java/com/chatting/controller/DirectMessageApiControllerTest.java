package com.chatting.controller;

import com.chatting.config.TestConfig;
import com.common.chat.response.DirectMessageResponse;
import com.core.api_core.chat.model.DirectMessage;
import com.core.api_core.chat.repository.DirectMessageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.core.directmessage.DirectMessageBuilder.createDirectMessage;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DirectMessageApiControllerTest {

    @Test
    void test() {

    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectMessageRepository directMessageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 두_사용자가_주고받은_채팅_조회_테스트() throws Exception {
        //given
        Long getterId = 1L;
        Long providerId = 2L;
        for(Integer i=0; i<3; i++) {
            directMessageRepository.save(createDirectMessage(i.toString()));
        }

        //when
        ResultActions resultActions =  mockMvc.perform(get("/api/dm/history?user1Id="+getterId+"&user2Id="+providerId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer some-valid-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        List<DirectMessageResponse> responses = objectMapper.convertValue(root, new TypeReference<>(){});

        //then
        Assertions.assertThat(responses.size()).isEqualTo(3);
    }

    @Test
    public void 두_사용자가_주고받은_마지막_채팅_정보_조회() throws Exception {
        Long getterId = 1L;
        Long providerId = 2L;
        for(int i=0; i<3; i++) {
            directMessageRepository.save(createDirectMessage("message" + i));
        }

        ResultActions resultActions =  mockMvc.perform(get("/api/dm/last/information?user1Id="+getterId+"&user2Id="+providerId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer some-valid-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        DirectMessage directMessage = objectMapper.convertValue(root, new TypeReference<>(){});

        Assertions.assertThat(directMessage.getMessage()).isEqualTo("message2");
    }
}
