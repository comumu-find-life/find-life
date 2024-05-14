package com.service.home.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Location {
    private double lat;
    private double lng;

    // Constructors, getters, and setters
}