package com.service.home.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SimpleHomeDto {
    private String address;
    private String mainImage;
    private Integer rentFee;
    private Integer bond;
    private Integer bill;
    private Integer rent;
    private Integer bedroomCount;
    private Integer bathRoomCount;
    private String type;
}