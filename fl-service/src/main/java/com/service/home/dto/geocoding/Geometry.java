package com.service.home.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {
    private Location location;

    // Constructors, getters, and setters
}