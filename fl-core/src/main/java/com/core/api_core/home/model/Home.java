package com.core.api_core.home.model;

import com.core.base.BaseTimeEntity;
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

    @JsonIgnore
    @OneToMany(mappedBy = "home", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HomeImage> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_address_id")
    private HomeAddress homeAddress;

    @Embedded
    private HomeInfo homeInfo;

    @Enumerated(EnumType.STRING)
    private HomeStatus homeStatus;

    public String getMainImage(){
        return images.get(0).getImageUrl();
    }

    public void setLatLng(double lat, double lng) {
        homeAddress.setLatLnd(lat, lng);
    }

    public void setStatus(HomeStatus status) {
        this.homeStatus = status;
    }

    public void setImages(List<HomeImage> images) {
        this.images = images;
    }

    public void addImages(List<HomeImage> images){
        this.images.addAll(images);
    }
}