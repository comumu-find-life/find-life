package com.service.home.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingResponse {
    private String status;
    private GeocodingResult[] results;

    // Constructors, getters, and setters
}