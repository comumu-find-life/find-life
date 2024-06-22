package com.service.home.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 제거 예정 HomeListOvervireResponse로 대체
 */
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
        private Long userIdx;
        private String userName;
}