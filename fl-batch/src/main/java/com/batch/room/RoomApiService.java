package com.batch.room;

import com.service.home.dto.HomeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomApiService {

    @Value("${domain.api}")
    private String baseUrl;

    public List<HomeDto> findRoomByCity(String city) {


        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/homes";

        HomeDto[] homeDtos = restTemplate.getForObject(url, HomeDto[].class);

        return Arrays.asList(homeDtos);

    }
}
