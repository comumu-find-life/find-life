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

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class HomeDto {

    private Long id;

    private Long userId;

    private HomeAddressDto homeAddress;

    private List<String> images;

    private Integer bathRoomCount;

    private Integer bond;

    private Gender gender;

    private Integer peopleCount;

    private HomeType type;

    private String shortIntroduce;

    private String introduce;

    private Integer bill;

}