package com.service.home;

import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.LatLng;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

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
                .state("NSW")
                .city("Sydney")
                .postCode(2000)
                .streetNumber("10")
                .streetName("BridgeStreet")
                .build();
        //when
        LatLng latLngFromAddress = locationService.getLatLngFromAddress(address);

        //then
        assertThat(latLngFromAddress.getLat()).isEqualTo(-33.8633957);
        assertThat(latLngFromAddress.getLng()).isEqualTo(151.2081836);
    }


}
