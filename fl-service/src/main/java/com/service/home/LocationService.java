package com.service.home;

import com.core.api_core.home.dto.HomeAddressGeneratorRequest;
import com.service.home.utils.LatLng;

public interface LocationService {
    LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto) throws IllegalAccessException;
}
