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

    private String bathroomType;

    private String minimumStay;

    private LocalDate date;

    private String residentType;

    private String isFurnished;

    private boolean canParking;

    private int bathRoomCount;

    private int bedroomCount;

    private Integer residentCount;

    private boolean dealSavable;

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
