package com.service.home;

import com.core.domain.home.dto.HomeAddressGeneratorRequest;
import com.service.home.utils.LatLng;

public interface LocationService {
    LatLng getLatLngFromAddress(final HomeAddressGeneratorRequest homeAddressDto) throws IllegalAccessException;
}
