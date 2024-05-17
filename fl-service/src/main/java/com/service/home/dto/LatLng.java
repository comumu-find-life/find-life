package com.service.home.dto;

import lombok.Getter;

@Getter
public class LatLng {
    private double lat;
    private double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // Getters and setters
}