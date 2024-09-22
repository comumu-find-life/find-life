package com.core.helper;

import com.core.api_core.home.model.*;
import com.core.api_core.user.model.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeHelper {

    public static Home generateHome(){
        return Home.builder()
                .userIdx(1L)
                .images(generateHomeImages())
                .homeAddress(generateHomeAddress())
                .homeInfo(generateHomeInfo())
                .homeStatus(HomeStatus.FOR_SALE)
                .build();
    }

    private static HomeInfo generateHomeInfo(){
        return HomeInfo.builder()
                .bathroomType("Private")  // 화장실 타입 (예: Private)
                .minimumStay("6 months")  // 최소 거주 기간 (예: 6개월)
                .date(LocalDate.now())  // 현재 날짜로 설정
                .residentType("Family")  // 거주자 유형 (예: Family)
                .isFurnished("Yes")  // 가구 포함 여부 (예: Yes)
                .canParking(true)  // 주차 타입 (예: Underground)
                .bathRoomCount(2)  // 화장실 개수
                .bedroomCount(3)  // 침실 개수
                .residentCount(4)  // 입주자 수
                .dealSavable(true)  // 안전 거래 가능 여부
                .options("A_S,DESK,CHAIR")  // 옵션 (예: A_S, DESK, CHAIR)
                .bond(1000)  // 보증금 (예: 1000)
                .introduce("A cozy and modern home.")  // 집 소개
                .bill(200)  // 관리비 (예: 물, 전기, 가스 등)
                .rent(1500)  // 월세 (예: 1500)
                .gender(Gender.FEMALE)  // 성별 (예: FEMALE)
                .type(HomeType.WHOLE_PROPERTY_RENT)  // 집 유형 (예: Apartment)
                .build();
    }

    private static HomeAddress generateHomeAddress(){
        return HomeAddress.builder()
                .state("NSW")
                .city("cityName")
                .postCode(2000)
                .detailAddress("201호")
                .streetCode("street")
                .streetCode("123")
                .latitude(35.123)
                .longitude(164.123)
                .build();
    }

    private static List<HomeImage> generateHomeImages(){
        List<HomeImage> images = new ArrayList<>();
        for(int i=0; i<3; i++){
            HomeImage build = HomeImage.builder()
                    .imageUrl("IMAGE" + i)
                    .build();
            images.add(build);
        }
        return images;
    }
}
