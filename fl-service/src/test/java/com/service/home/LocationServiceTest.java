package com.service.home;

import com.common.home.request.HomeAddressGeneratorRequest;
import com.service.home.utils.LatLng;
import com.service.home.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LocationServiceTest {

    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp()  {
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

        System.out.println(latLngFromAddress.getLat());

        //then
    }

}
