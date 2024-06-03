package com.batch.chat;

import com.core.chat.dto.DirectMessageRoomInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

    @Value("${domain.api}")
    private String baseUrl;

    public DirectMessageRoomInfoDto findDmRoomInfoByRoomId(String token, Long roomId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // HttpHeaders 객체 생성
        headers.setBearerAuth(token); // Bearer 토큰 추가
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정

        HttpEntity requestEntity = new HttpEntity<>(headers); // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)

        String url = baseUrl + "/dm/dm-rooms/" + roomId;
        // 요청 보내기
        ResponseEntity<DirectMessageRoomInfoDto> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                DirectMessageRoomInfoDto.class);

        return responseEntity.getBody();
    }
}
