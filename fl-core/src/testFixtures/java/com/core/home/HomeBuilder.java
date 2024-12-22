package com.core.home;

import com.core.api_core.home.model.*;
import com.core.api_core.user.model.Gender;

import java.util.ArrayList;
import java.util.List;

public class HomeBuilder {

    public static Home createHome(){
        return Home.builder()
                .homeInfo(createHomeInfo())
                .homeStatus(HomeStatus.FOR_SALE)
                .homeAddress(createHomeAddress())
                .images(createHomeImages())
                .userIdx(1L)
                .build();
    }

    private static List<HomeImage> createHomeImages() {
        List<HomeImage> images = new ArrayList<>();
        for(int i=0; i<3; i++){
            images.add(HomeImage.builder()
                            .imageUrl("imageURL" + i)
                    .build());
        }
        return images;
    }

    private static HomeAddress createHomeAddress(){
        return HomeAddress.builder().build();
    }

    private static HomeInfo createHomeInfo(){
        return HomeInfo.builder()
                .bathroomType("bathroomType")
                .minimumStay("10")
                .residentType("12")
                .isFurnished("12")
                .canParking(true)
                .bedroomCount(10)
                .residentCount(10)
                .dealSavable(true)
                .options("options")
                .bond(1000)
                .introduce("inin")
                .bill(1000)
                .rent(3000)
                .gender(Gender.ANYTHING)
                .type(HomeType.HOME_STAY)
                .build();
    }
}
