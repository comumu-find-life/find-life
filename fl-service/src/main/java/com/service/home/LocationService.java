package com.service.home;

import com.common.home.request.HomeAddressGeneratorRequest;
import com.service.home.utils.LatLng;

public interface LocationService {
    LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto) throws IllegalAccessException;

}
