package com.service.home;

import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.LatLng;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LocationServiceTest {

    private LocationService locationService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        locationService = new LocationService();
    }

    @Test
    void 주소_검색_테스트() throws  IOException{
        //given

        //then
        locationService.searchPlaceInAustralia("Par");

        //then
    }

    @Test
    void 주소_위도_경도_변환_테스트() throws IOException {
        //given
        HomeAddressGeneratorRequest address = HomeAddressGeneratorRequest.builder()
                .state("VIC")
                .city("Melbourne")
                .postCode(3000)
                .streetNumber("121")
                .build();
        //when
        LatLng latLngFromAddress = locationService.getLatLngFromAddress(address);

        System.out.println(latLngFromAddress.getLat());
        System.out.println(latLngFromAddress.getLng());
        //then
        //Assertions.assertThat(latLngFromAddress.getLat()).isEqualTo(-33.8093134);
        //Assertions.assertThat(latLngFromAddress.getLng()).isEqualTo(150.9566326);
    }


}
