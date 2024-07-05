package com.core.home.model;

import com.core.base.model.BaseTimeEntity;
import com.core.user.model.Gender;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Home extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_id")
    private Long id;

    //UserId 와 연관관계 매핑
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    private Long userIdx;

    //집 사진
    @JsonIgnore
    @OneToMany(mappedBy = "home", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HomeImage> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_address_id")
    private HomeAddress homeAddress;

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

    // 판매중 or 판매 완료
    @Enumerated(EnumType.STRING)
    private HomeStatus status;

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

    /**
     * 연관관계 등록 메서드
     */
    public void setLatLng(double lat, double lng) {
        homeAddress.setLatLnd(lat, lng);
    }

    public void setStatus(HomeStatus status) {
        this.status = status;
    }

    public void setImages(List<HomeImage> images) {
        this.images = images;
    }

}