package com.batch.home;

import com.common.home.request.HomeGeneratorRequest;
import com.common.home.response.HomeInformationResponse;
import com.common.utils.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeApiService {

    @Value("${domain.api}")
    private String baseUrl;

    @Value("${domain.home}")
    private String homeUrl;

    @Value("${domain.home-city}")
    private String homeByCityUrl;

    private final ObjectMapper objectMapper;


    public <T> List<T> findRooms() {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/homes?size=9";

        // API 응답을 SuccessResponse로 받음
        SuccessResponse response = restTemplate.getForObject(url, SuccessResponse.class);
        log.info(response.getData().toString());
        return (List<T>) response.getData();
    }

    public <T> List<T> findRoomByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = homeByCityUrl +"/"+ city;

        // API 응답을 SuccessResponse로 받음
        SuccessResponse response = restTemplate.getForObject(url, SuccessResponse.class);
        return (List<T>) response.getData();
    }

    public HomeInformationResponse findRoomByRoomId(Long homeId) {

        RestTemplate restTemplate = new RestTemplate();

        SuccessResponse response = restTemplate.getForObject(homeUrl +"/"+ homeId, SuccessResponse.class);
        HomeInformationResponse homeDto = objectMapper.convertValue(response.getData(), HomeInformationResponse.class);
        log.info(homeDto.getAddress());


        return homeDto;
    }


    public void addHomePost(HomeGeneratorRequest homeRequest, List<MultipartFile> images, String token) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // JSON 데이터 설정
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<HomeGeneratorRequest> homeRequestEntity = new HttpEntity<>(homeRequest, jsonHeaders);

        // 이미지 파일 설정
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("homeCreateDto", homeRequestEntity);

        for (MultipartFile image : images) {
            Resource imageResource = new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            };
            body.add("images", imageResource);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        headers.setBearerAuth(token); // Bearer 토큰 추가
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 요청 보내기
        ResponseEntity<SuccessResponse> response = restTemplate.exchange(homeUrl, HttpMethod.POST, requestEntity, SuccessResponse.class);

        // 응답 처리
        if (response.getStatusCode().is2xxSuccessful()) {
            SuccessResponse successResponse = response.getBody();
            // 성공 응답 처리
        } else {
            // 실패 응답 처리
        }
    }
}
