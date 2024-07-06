package com.common.home.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HomeListOverviewResponse {
        private Long id;
        private String postcode;
        private String city;
        private String detailAddress;
        private double latitude;
        private double longitude;
        private String mainImage;
        private Integer rent;
        private Integer bond;
        private Integer bill;
        private Integer bedroomCount;
        private Integer bathRoomCount;
        private String type;
        private Long userIdx;
        private String userName;
}