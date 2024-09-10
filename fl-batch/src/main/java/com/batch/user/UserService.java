package com.batch.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

//    public UserProfileResponse getLoginUserProfile(String token) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders(); // HttpHeaders 객체 생성
//        headers.setBearerAuth(token); // Bearer 토큰 추가
//        headers.setContentType(MediaType.APPLICATION_JSON); // 요청을 보낼 때 JSON으로 요청하기 위해 Content-Type 설정
//
//        HttpEntity<ApplicationDmFormRequest> requestEntity = new HttpEntity<>(headers); // HttpEntity 객체 생성 (요청 본문 및 헤더 설정)
//
//        // 요청 보내기
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                requestEntity,
//                String.class);
//    }
}
