package com.batch.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ChatApiService {

    @Value("${domain.api}")
    private String baseUrl;

    public void applicationDm(ApplicationDmFormDto applicationDmFormDto, String token) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // HttpHeaders 객체 생성
        headers.setBearerAuth(token); // Bearer 토큰 추가
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정

        HttpEntity<ApplicationDmFormDto> requestEntity = new HttpEntity<>(applicationDmFormDto, headers); // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)

        String url = baseUrl + "/dm/dm";
        // 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class);

    }

    public void findDmRoomsByUserId(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders(); // HttpHeaders 객체 생성
        headers.setBearerAuth(token); // Bearer 토큰 추가
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정

        HttpEntity requestEntity = new HttpEntity<>(headers); // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)

        String url = baseUrl + "/dm-room";
        // 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class);
    }
}
