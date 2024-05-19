package com.service.home.dto.request;

import com.core.home.model.HomeType;
import com.core.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class HomeGeneratorRequest {

    private Long userId;

    private List<String> images;

    private HomeAddressGeneratorRequest homeAddress;

    private Integer bathRoomCount;

    private boolean dealSavable;

    private Integer bedroomCount;

    private Integer bond;

    private Gender gender;

    private HomeType type;

    private String introduce;

    private Integer bill;

    private Integer rent;

}