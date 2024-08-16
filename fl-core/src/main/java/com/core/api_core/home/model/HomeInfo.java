package com.core.api_core.home.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeInfo {

    private String bathroomType;

    private String minimumStay;

    private LocalDate date;

    private String residentType;

    private String isFurnished;

    private String parkingType;
}
