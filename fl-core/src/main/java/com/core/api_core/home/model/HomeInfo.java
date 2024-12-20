package com.core.api_core.home.model;

import com.core.api_core.user.model.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeInfo {

    //침대 타입
    private String bathroomType;

    //최소 머물 기간
    private String minimumStay;

    private LocalDate date;

    private String residentType;

    private String isFurnished;

    //주차 여부
    private boolean canParking;

    // 화장실 개수
    private int bathRoomCount;

    // Bedroom / 침실 개수
    private int bedroomCount;

    // Resident / 입주자 수
    private Integer residentCount;

    // 안전 거래 가능 여부
    private boolean dealSavable;

    // ex) A_S,DESK,CHAIR , 로 옵션 분리
    private String options;

    //보증금
    private Integer bond;

    //집 소개 내용
    private String introduce;

    // 관리비(물, 전기, 가스, 인터넷 등) / bill
    private Integer bill;

    // 주세/ Rent
    private Integer rent;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private HomeType type;


}
