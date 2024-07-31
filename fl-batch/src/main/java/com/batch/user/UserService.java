package com.batch.user;

import com.batch.chat.ApplicationDmFormRequest;
import com.common.home.request.HomeGeneratorRequest;
import com.common.user.response.UserProfileResponse;
import com.common.utils.SuccessResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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
