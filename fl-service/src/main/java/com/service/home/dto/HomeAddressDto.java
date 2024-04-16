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

}
