package com.core.api_core.home.model;

import com.core.base.model.BaseTimeEntity;
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

    private Long userIdx;

    //집 사진
    @JsonIgnore
    @OneToMany(mappedBy = "home", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HomeImage> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_address_id")
    private HomeAddress homeAddress;

    @Embedded
    private HomeInfo homeInfo;

    // 판매중 or 판매 완료
    @Enumerated(EnumType.STRING)
    private HomeStatus homeStatus;

    //조회수
    private Integer viewCount;

    /**
     * 연관관계 등록 메서드
     */
    public void setLatLng(double lat, double lng) {
        homeAddress.setLatLnd(lat, lng);
    }

    public void setStatus(HomeStatus status) {
        this.homeStatus = status;
    }

    public void setImages(List<HomeImage> images) {
        this.images = images;
    }

    public String getMainImage(){
        return images.get(0).getImageUrl();
    }

}