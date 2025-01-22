package com.core.api_core.home.model;

import com.core.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeAddress extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_address_id")
    private Long id;

    private String state;

    private String city;

    private Integer postCode;

    private String detailAddress;

    private String streetName;

    private String streetCode;

    private double latitude;

    private double longitude;

    protected void setLatLnd(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String parseAddress(){
        StringBuilder sb = new StringBuilder();
        sb.append(streetCode+" ");
        sb.append(streetName+",");
        sb.append(city+" ");
        sb.append(state+" ");
        sb.append(postCode);
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HomeAddress)) return false;
        HomeAddress that = (HomeAddress) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}