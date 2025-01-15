package com.common.home.response;

import com.core.api_core.home.model.HomeStatus;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 제거 예정 HomeListOvervireResponse로 대체
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
        private HomeStatus homeStatus;
}