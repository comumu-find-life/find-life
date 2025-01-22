package com.core.api_core.home.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeAddressGeneratorRequest {

    // ex) 3000 우편번호
    private Integer postCode;

    // ex) NSW
    private String state;

    // ex) Sydney
    private String city;

    // ex) Bridge Street 거리 이름
    private String streetName;

    // ex) 상세 주소 ex) 401 호
    private String detailAddress;

    // ex) 건물 번호 10
    private String streetCode;

    private double latitude;

    private double longitude;

}
