package com.service.home;

import com.common.home.request.HomeAddressGeneratorRequest;
import com.service.home.dto.geocoding.LatLng;

public interface LocationService {
    LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto) throws IllegalAccessException;

}
