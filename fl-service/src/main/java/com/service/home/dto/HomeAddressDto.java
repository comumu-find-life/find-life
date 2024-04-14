package com.service.home.dto;

import com.core.home.model.HomeAddress;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeAddressDto {

    private String state;

    private String city;

    private Integer postCode;

    private String detailAddress;

    private String streetName;

    private String streetNumber;

    public HomeAddress toEntity() {
        return HomeAddress.builder()
                .state(state)
                .city(city)
                .postCode(postCode)
                .detailAddress(detailAddress)
                .streetName(streetName)
                .streetNumber(streetNumber)
                .build();
    }

}
