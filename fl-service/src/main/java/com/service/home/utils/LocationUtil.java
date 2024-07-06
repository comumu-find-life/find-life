package com.service.home.utils;


import com.common.home.request.HomeAddressGeneratorRequest;

public class LocationUtil {
    static public String toStringAddress(HomeAddressGeneratorRequest addressDto){
        StringBuilder sb = new StringBuilder();

        // 거리번호
        sb.append(addressDto.getStreetCode());
        //거리 이름
        sb.append(addressDto.getStreetName().trim().replaceAll("\\s+", ""));
        // todo 항상 street?
        sb.append(",");
        //city 이름
        //sb.append(addressDto.getCity()+",");
        // 주
        sb.append(addressDto.getCity());
        sb.append(addressDto.getState()+",");
        //우편주소
        sb.append(addressDto.getPostCode());
        sb.append("Australia");

        return sb.toString();
    }
}
