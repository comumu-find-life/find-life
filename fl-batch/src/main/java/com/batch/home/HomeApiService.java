package com.batch.home;

import com.common.home.response.HomeInformationResponse;
import com.common.utils.SuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


    public void addHomePost(HomeRequest homeRequest) {
        RestTemplate restTemplate = new RestTemplate();

        SuccessResponse response = restTemplate.postForObject(homeUrl, homeRequest, SuccessResponse.class);
    }
}
