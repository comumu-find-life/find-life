package com.service.home.dto;

import com.core.user.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SimpleHomeDto {

    private Long id;
    private String address;
    private String mainImage;
    private Integer rent;
    private Integer bond;
    private Integer bill;
    private Integer bedroomCount;
    private Integer bathRoomCount;
    private String type;
    private Long userId;
    private String userName;
}