package com.service.home.dto.response;

import com.core.home.model.HomeType;
import com.core.user.model.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HomeInformationResponse {

    /**
     *  집주인 정보
     */
    private String providerId;

    private String providerProfileUrl;

    private String providerName;

    // 주소
    private String address;

    private List<String> images;

    private Integer bill;

    private Integer rent;

    private HomeType type;

    private Gender gender;

    private String introduce;

    // 화장실 개수
    private Integer bathRoomCount;

    // Bedroom / 침실 개수
    private Integer bedroomCount;

}
