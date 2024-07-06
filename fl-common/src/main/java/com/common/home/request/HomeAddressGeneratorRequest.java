package com.common.home.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeAddressGeneratorRequest {

    // ex) NSW
    private String state;

    // ex) Sydney
    private String city;

    // ex) 3000 우편번호
    private Integer postCode;

    // ex) 상세 주소 ex) 401 호
    private String detailAddress;

    // ex) Bridge Street 거리 이름
    private String streetName;

    // ex) 건물 번호 10
    private String streetCode;

}
