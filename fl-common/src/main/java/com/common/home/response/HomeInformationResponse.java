package com.common.home.response;

import com.core.api_core.home.model.HomeInfo;
import com.core.api_core.home.model.HomeType;
import com.core.api_core.user.model.Gender;
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

    private Long homeId;
    // 주소
    private String address;

    private List<String> images;

    private Integer bond;

    private Integer bill;

    private Integer rent;

    private HomeType type;

    private Gender gender;

    private String introduce;

    private String options;

    // 화장실 개수
    private Integer bathRoomCount;

    // Bedroom / 침실 개수
    private Integer bedroomCount;

    // Resident 입주자 수
    private Integer residentCount;

    private double latitude;

    private double longitude;

    // DTO 는 도메인과 완전히 분리 되어야해.
//    private HomeInfo homeInfo;
}
