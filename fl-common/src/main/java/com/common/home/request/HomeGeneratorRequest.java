package com.common.home.request;

import com.core.home.model.HomeType;
import com.core.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HomeGeneratorRequest {

    private Long userIdx;

    private HomeAddressGeneratorRequest homeAddress;

    private Integer bathRoomCount;

    private Integer residentCount;

    private boolean dealSavable;

    private Integer bedroomCount;

    private Integer bond;

    private Gender gender;

    private HomeType type;

    private String introduce;

    private Integer bill;

    private Integer rent;

    private String options;

}