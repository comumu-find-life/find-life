package com.core.domain.home.model;

import com.core.domain.user.model.Gender;
import jakarta.persistence.*;
import lombok.*;


@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeInfo {

    private boolean canParking;

    private int bathRoomCount;

    private int bedroomCount;

    private Integer residentCount;

    private String options;

    private Integer bond;

    private String introduce;

    private Integer bill;

    private Integer rent;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private HomeType type;


}
