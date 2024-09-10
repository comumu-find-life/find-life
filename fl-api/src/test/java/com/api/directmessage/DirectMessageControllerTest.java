//package com.api.directmessage;
//
//import com.api.config.TestConfig;
//import com.api.security.service.JwtService;
//import com.core.api_core.chat.repository.DirectMessageRoomRepository;
//import com.core.chat_core.chat.repository.DirectMessageRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@ContextConfiguration(classes = TestConfig.class)
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//@Transactional
//public class DirectMessageControllerTest {
//    @MockBean
//    private SecurityFilterChain securityFilterChain;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private DirectMessageRepository directMessageRepository;
//
//    @Autowired
//    private DirectMessageRoomRepository directMessageRoomRepository;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private String token;
//
//
//    @BeforeEach
//    public void setUp() {
//        token = "Bearer your-jwt-token";
//    }
//
//    @Test
//    void 자신의_모든_채팅목록_조회_테스트() throws Exception {
////        given
////        directMessageRoomRepository.save(generateDirectMessageRoom());
////        directMessageRepository.save(generateDirectMessage());
////
////        //when
////        ResultActions resultActions = mockMvc.perform(get(DM_FIND_ALL_ROOMS,1L)
////                        .header(HttpHeaders.AUTHORIZATION, token)
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
////
////        String responseString = resultActions.andReturn().getResponse().getContentAsString();
////        JsonNode root = objectMapper.readTree(responseString);
////        JsonNode dataNode = root.path("data");
////        List<DirectMessageRoomListResponse> responses = objectMapper.convertValue(dataNode, new TypeReference<>(){});
////
////  //      then
//    }
//}
