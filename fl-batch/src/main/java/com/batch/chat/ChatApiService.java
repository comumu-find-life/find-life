package com.batch.chat;

import com.common.chat.response.DirectMessageRoomListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class ChatApiService {

    @Value("${domain.api}")
    private String baseUrl;


    @Value("${domain.dm-room.list}")
    private String dmRoomsUrl;

    public void applicationDm(ApplicationDmFormRequest applicationDmFormDto, String token) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // HttpHeaders 객체 생성
        headers.setBearerAuth(token); // Bearer 토큰 추가
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정

        HttpEntity<ApplicationDmFormRequest> requestEntity = new HttpEntity<>(applicationDmFormDto, headers); // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)

        String url = baseUrl + "/dm";
        // 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class);

    }

    public List<DirectMessageRoomListResponse> findDmRoomsByUserId(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // HttpHeaders 객체 생성
        headers.setBearerAuth(token); // Bearer 토큰 추가
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정

        HttpEntity requestEntity = new HttpEntity<>(headers); // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)

        // 반환 타입 설정
        ParameterizedTypeReference<List<DirectMessageRoomListResponse>> responseType = new ParameterizedTypeReference<List<DirectMessageRoomListResponse>>() {};

        // 요청 보내기
        ResponseEntity<List<DirectMessageRoomListResponse>> dmRooms = restTemplate.exchange(
                dmRoomsUrl,
                HttpMethod.GET,
                requestEntity,
                responseType);

        return dmRooms.getBody();
    }

    public List<DirectMessageRoomListResponse> findDmLogs(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // HttpHeaders 객체 생성
        headers.setBearerAuth(token); // Bearer 토큰 추가
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정

        HttpEntity requestEntity = new HttpEntity<>(headers); // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)

        // 반환 타입 설정
        ParameterizedTypeReference<List<DirectMessageRoomListResponse>> responseType = new ParameterizedTypeReference<List<DirectMessageRoomListResponse>>() {};

        // 요청 보내기
        ResponseEntity<List<DirectMessageRoomListResponse>> dmRooms = restTemplate.exchange(
                dmRoomsUrl,
                HttpMethod.GET,
                requestEntity,
                responseType);

        return dmRooms.getBody();
    }
}
