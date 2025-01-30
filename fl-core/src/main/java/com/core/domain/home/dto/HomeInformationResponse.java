package com.core.domain.home.dto;

import com.core.domain.home.model.HomeStatus;
import com.core.domain.home.model.HomeType;
import com.core.domain.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    private boolean canParking;

    private HomeStatus homeStatus;

    // DTO 는 도메인과 완전히 분리 되어야해.
//    private HomeInfo homeInfo;
}
