package com.batch.room;

import com.service.home.dto.HomeDto;
import com.service.home.dto.SimpleHomeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RoomApiService {

    @Value("${domain.api}")
    private String baseUrl;

    public List<SimpleHomeDto> findRoomByCity(String city) {

        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/homes/" + city;

        SimpleHomeDto[] homeDtos = restTemplate.getForObject(url, SimpleHomeDto[].class);

        return Arrays.asList(homeDtos);
    }

    public SimpleHomeDto findRoomByRoomId(Long roomId) {

        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/home/" + roomId;

        SimpleHomeDto homeDtos = restTemplate.getForObject(url, SimpleHomeDto.class);

        return homeDtos;
    }
}
