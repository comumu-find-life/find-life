package com.core.home.request;

import com.core.api_core.home.dto.HomeAddressGeneratorRequest;
import com.core.api_core.home.dto.HomeGeneratorRequest;
import com.core.api_core.home.dto.HomeUpdateRequest;
import com.core.api_core.home.model.HomeType;
import com.core.api_core.user.model.Gender;

public class HomeRequestBuilder {

    public static HomeGeneratorRequest createHomeGeneratorRequest() {

        return HomeGeneratorRequest.builder()
                .homeAddress(createHomeAddressGeneratorRequest())
                .rent(500)
                .bond(1000)
                .bill(15)
                .bedroomCount(10)
                .bathRoomCount(5)
                .residentCount(3)
                .dealSavable(true)
                .canParking(true)
                .introduce("INTRODUCE")
                .gender(Gender.MALE)
                .type(HomeType.HOME_STAY)
                .build();
    }

    public static HomeUpdateRequest createHomeUpdateRequest(Long homId) {
        return HomeUpdateRequest.builder()
                .homeId(homId)
                .homeAddress(createHomeAddressGeneratorRequest())
                .bathRoomCount(100)
                .dealSavable(false)
                .bedroomCount(2)
                .bond(2000)
                .gender(Gender.FEMALE)
                .type(HomeType.SHARED_ROOM)
                .introduce("INTRODUCEE")
                .bill(50)
                .rent(500)
                .options("asdasdasd")
                .build();
    }

    public static HomeAddressGeneratorRequest createHomeAddressGeneratorRequest(){
        return HomeAddressGeneratorRequest.builder()
                .postCode(2000)
                .state("NSW")
                .city("Sydney")
                .streetCode("123")
                .streetName("street Name")
                .detailAddress("401호")
                .build();
    }

}
