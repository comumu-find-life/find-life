package com.service.home;

import com.service.home.dto.geocoding.LatLng;
import com.service.home.dto.request.HomeAddressGeneratorRequest;

public interface LocationService {
    LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto) ;

}
