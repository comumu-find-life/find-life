package com.service.home;

import com.service.home.dto.LatLng;
import com.service.home.dto.request.HomeAddressGeneratorRequest;

public interface LocationService {
    LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto) ;
}
