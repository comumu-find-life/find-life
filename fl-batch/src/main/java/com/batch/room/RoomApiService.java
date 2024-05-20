package com.batch.room;

import com.service.home.dto.HomeOverviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomApiService {

    @Value("${domain.api}")
    private String baseUrl;

    public List<HomeOverviewResponse> findRoomByCity(String city) {

        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/homes/" + city;

        HomeOverviewResponse[] homeDtos = restTemplate.getForObject(url, HomeOverviewResponse[].class);

        return Arrays.asList(homeDtos);
    }
}
