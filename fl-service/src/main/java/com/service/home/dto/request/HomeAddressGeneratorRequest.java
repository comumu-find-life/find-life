package com.service.home.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeAddressGeneratorRequest {

    // ex) NSW
    private String state;

    private String city;

    private Integer postCode;

    private String detailAddress;

    private String streetName;

    private String streetNumber;

}
