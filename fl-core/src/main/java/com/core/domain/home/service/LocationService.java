package com.core.domain.home.service;

import com.core.domain.home.dto.HomeAddressGeneratorRequest;
import com.core.domain.home.model.LatLng;

public interface LocationService {
    LatLng getLatLngFromAddress(final HomeAddressGeneratorRequest homeAddressDto) throws IllegalAccessException;
}
