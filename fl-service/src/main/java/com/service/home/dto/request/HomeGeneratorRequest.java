package com.service.home.dto;

import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.home.model.HomeImage;
import com.core.home.model.HomeType;
import com.core.user.model.Gender;
import com.core.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class HomeGeneratorRequest {

    private User user;

    private HomeAddressDto homeAddress;

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

    private String options;

}