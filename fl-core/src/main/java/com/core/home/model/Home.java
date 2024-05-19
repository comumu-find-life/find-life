package com.core.home.model;

import com.core.base.model.BaseTimeEntity;
import com.core.user.model.Gender;
import com.core.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Home extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_id")
    private Long id;

    //UserId 와 연관관계 매핑
    private Long userId;

    //집 사진
    @JsonIgnore
    @OneToMany(mappedBy = "home" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HomeImage> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_address_id")
    private HomeAddress homeAddress;

    // 화장실 개수
    private Integer bathRoomCount;

    // Bedroom / 침실 개수
    private Integer bedroomCount;

    // 안전 거래 가능 여부
    private boolean dealSavable;

    //보증금
    private Integer bond;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private HomeType type;

    //집 소개 내용
    private String introduce;

    // 관리비(물, 전기, 가스, 인터넷 등) / bill
    private Integer bill;

    // 주세 or 월세 / Rent
    private Integer rent;

    //조회수
    private Integer viewCount;

    public void update(Home home){
        this.homeAddress = home.homeAddress;
        this.bathRoomCount = home.bathRoomCount;
        this.bond = home.bond;
        this.gender = home.gender;
        this.introduce = home.introduce;
        this.bill = home.bill;
        this.rent = home.rent;
    }

    /**
     * 연관관계 등록 메서드
     */
    public void setLatLng(double lat, double lng){
        homeAddress.setLatLnd(lat, lng);
    }


    public void setImages(List<HomeImage> images) {
        this.images = images;
    }

}