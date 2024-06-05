package com.service.home.dto.response;

import com.core.user.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HomeOverviewResponse {
    private Long id;
    private String address;
    private double latitude;
    private double longitude;
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