package com.core.domain.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HomeAddressUpdateRequest {
    private Long homeId;
    private String state;
    private String city;
    private String streetName;
    private String detailAddress;
    private String streetCode;
}
