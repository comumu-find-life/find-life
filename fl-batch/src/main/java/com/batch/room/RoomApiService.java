package com.batch.room;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.dto.HomeOverviewResponse;
import com.service.utils.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class RoomApiService {

    @Value("${domain.api}")
    private String baseUrl;


    public <T> List<T> findRoomByCity(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/homes/" + city;

        // API 응답을 SuccessResponse로 받음
        SuccessResponse response = restTemplate.getForObject(url, SuccessResponse.class);

        return (List<T>) response.getData();
    }

    public HomeOverviewResponse findRoomByRoomId(Long roomId) {

        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/home/" + roomId;

        HomeOverviewResponse homeDtos = restTemplate.getForObject(url, HomeOverviewResponse.class);

        return homeDtos;
    }
}
