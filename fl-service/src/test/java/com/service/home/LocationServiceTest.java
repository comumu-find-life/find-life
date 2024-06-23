package com.service.home;

import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.geocoding.LatLng;
import com.service.home.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationServiceTest {

    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        locationService = new LocationServiceImpl();
    }


    @Test
    void 주소_위도_경도_변환_테스트() throws IOException, IllegalAccessException {
        //given
        HomeAddressGeneratorRequest address = HomeAddressGeneratorRequest.builder()
                .state("NSW")
                .city("Sydney")
                .postCode(123)
                .streetCode("aaadddd")
                .streetName("123")
                .build();
        //when
        LatLng latLngFromAddress = locationService.getLatLngFromAddress(address);

        //then
    }

}
