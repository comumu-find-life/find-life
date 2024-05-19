package com.core.home.model;

import com.core.base.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_address_id")
    private Long id;


    // 주
    private String state;

    // 시티
    private String city;

    // 우편 변호
    private Integer postCode;

    //상세 주소 ex) 401호
    private String detailAddress;

    //거리 이름
    private String streetName;

    //거리 번호
    private String streetCode;

    //위도
    private double latitude;

    //경도
    private double longitude;

    protected void setLatLnd(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

}