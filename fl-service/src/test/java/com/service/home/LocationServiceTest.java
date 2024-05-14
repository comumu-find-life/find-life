package com.service.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.home.dto.LatLng;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class LocationServiceTest {

    private LocationService locationService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        locationService = new LocationService(restTemplate, objectMapper);
    }

    @Test
    void 주소_위도_경도_변환_테스트() throws IOException {
        //given
        String address = "2015,Australia";
        //when
        LatLng latLngFromAddress = locationService.getLatLngFromAddress(address);

        //then
        Assertions.assertThat(latLngFromAddress.getLat()).isEqualTo(-33.9087966);
        Assertions.assertThat(latLngFromAddress.getLng()).isEqualTo(151.195674);
    }
}
