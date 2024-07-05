package com.api.helper;

import com.core.home.model.*;
import com.core.user.model.Gender;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.request.HomeUpdateRequest;

import java.util.ArrayList;
import java.util.List;

public class HomeHelper {

    /**
     * 집 주소 요청 객체 생성 메서드
     */
    public static HomeAddressGeneratorRequest generateHomeAddressGeneratorReqeust() {
        return  HomeAddressGeneratorRequest.builder()
                .state("NSW")
                .city("Sydney")
                .postCode(2000)
                .detailAddress("401호")
                .streetCode("123")
                .streetName("King Street")
                .build();
    }

    /**
     * 집 게시글 생성 메서드
     */
    public static HomeGeneratorRequest generateHomeGeneratorRequest() {
        return HomeGeneratorRequest.builder()
                .userIdx(1L)
                .homeAddress(generateHomeAddressRequest())
                .bathRoomCount(5)
                .bedroomCount(1)
                .dealSavable(true)
                .bond(3000)
                .gender(Gender.MALE)
                .type(HomeType.WHOLE_PROPERTY_RENT)
                .introduce("This is a beautiful home")
                .bill(10)
                .rent(300)
                .options("TABLE,DESK,CHAIR")
                .build();
    }

    /**
     * 집 엔티티 생성 메서드
     */
    public static Home generateHomeEntity(){
        return Home.builder()
                .id(1L)
                .images(generateHomeImages())
                .bathRoomCount(10)
                .bedroomCount(1)
                .dealSavable(true)
                .options("TABLE,DESK,CHAIR")
                .bond(3000)
                .gender(Gender.MALE)
                .type(HomeType.SHARED_ROOM)
                .introduce("dasdasd")
                .bill(10)
                .rent(300)
                .status(HomeStatus.FOR_SALE)
                .homeAddress(generateHomeAddressEntity())
                .build();
    }


    /**
     * 집 주소 변경할 정보 생성 메서드
     */
    public static HomeUpdateRequest generateHomeUpdateRequest() {
        return HomeUpdateRequest.builder()
                .homeId(1L)
                .homeAddress(generateHomeAddressRequest())
                .bathRoomCount(100)
                .dealSavable(false)
                .bedroomCount(2)
                .bond(2000)
                .gender(Gender.FEMALE)
                .type(HomeType.RENT)
                .introduce("INTRODUCEE")
                .bill(3000)
                .rent(500)
                .options("asdasdasd")
                .build();
    }

    /**
     * 집 주소 엔티티 생성 메서드
     */
    private static HomeAddress generateHomeAddressEntity() {
        return HomeAddress.builder()
                .id(1L)
                .state("WAC")
                .city("Sydney")
                .postCode(3000)
                .detailAddress("401호")
                .longitude(-35.443)
                .latitude(151.1234)
                .streetCode("500")
                .streetName("Street Name")
                .build();
    }

    /**
     * 집 엔티티에 저장될 집 이미지 생성 메서드
     */
    private static List<HomeImage> generateHomeImages() {
        List<HomeImage> images = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            images.add(HomeImage.builder()
                    .imageUrl("URL" + i)
                    .build());
        }
        return images;
    }


    /**
     * 집 주소 생성 요청 메서드
     */

    public static HomeGeneratorRequest generateHomeGeneratorRequest() {
        return HomeGeneratorRequest.builder()
                .userIdx(1L)
                .homeAddress(generateHomeAddressRequest())
                .bathRoomCount(5)
                .bedroomCount(1)
                .dealSavable(true)
                .bond(3000)
                .gender(Gender.MALE)
                .type(HomeType.HOME_STAY)
                .introduce("This is a beautiful home")
                .bill(10)
                .rent(300)
                .options("TABLE,DESK,CHAIR")
                .build();
    }

    public static HomeUpdateRequest generateHomeUpdateRequest() {
        return HomeUpdateRequest.builder()
                .homeId(1L)
                .homeAddress(generateHomeAddressRequest())
                .bathRoomCount(100)
                .dealSavable(false)
                .bedroomCount(2)
                .bond(2000)
                .gender(Gender.FEMALE)
                .type(HomeType.SHARED_ROOM)
                .introduce("INTRODUCEE")
                .bill(3000)
                .rent(500)
                .options("asdasdasd")
                .build();
    }

    private static HomeAddressGeneratorRequest generateHomeAddressRequest() {
        return HomeAddressGeneratorRequest.builder()
                .state("NSW")
                .city("Sydney")
                .postCode(2000)
                .detailAddress("401호")
                .streetCode("10")
                .streetName("BridgeStreet")
                .build();
    }

}
