package com.api.config;

import com.core.home.model.*;
import com.core.user.model.Gender;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.request.HomeUpdateRequest;

import java.util.ArrayList;
import java.util.List;

public class HomeGenerator {

    public static Home generateHomeEntity(){
        return Home.builder()
                .id(1L)
                .userId(1L)
                .images(generateHomeImages())
                .bathRoomCount(10)
                .bedroomCount(1)
                .dealSavable(true)
                .options("TABLE,DESK,CHAIR")
                .bond(3000)
                .gender(Gender.MALE)
                .type(HomeType.RENT)
                .introduce("dasdasd")
                .bill(10)
                .rent(300)
                .status(HomeStatus.FOR_SALE)
                .homeAddress(generateHomeAddressEntity())
                .build();
    }

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

    private static List<HomeImage> generateHomeImages() {
        List<HomeImage> images = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            images.add(HomeImage.builder()
                    .imageUrl("URL" + i)
                    .build());
        }
        return images;
    }

    public static HomeGeneratorRequest generateHomeGeneratorRequest() {
        return HomeGeneratorRequest.builder()
                .userId(1L)
                .images(generateHomeImageUrls())
                .homeAddress(generateHomeAddressRequest())
                .bathRoomCount(5)
                .bedroomCount(1)
                .dealSavable(true)
                .bond(3000)
                .gender(Gender.MALE)
                .type(HomeType.RENT)
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
                .type(HomeType.RENT)
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

    private static List<String> generateHomeImageUrls() {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            imageUrls.add("URL" + i);
        }
        return imageUrls;
    }
}
