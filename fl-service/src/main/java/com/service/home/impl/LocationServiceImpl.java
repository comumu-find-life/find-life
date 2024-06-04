package com.service.home.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.home.LocationService;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URI;

import static com.service.home.utils.LocationUtil.toStringAddress;

@Service
public class LocationServiceImpl implements LocationService {

    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String API_KEY = "AIzaSyDiCJBIUrDSpEKeGIWFKC01_7-fWQhM1bg";

    private final RestTemplate restTemplate;

    @Autowired
    public LocationServiceImpl( ) {
        this.restTemplate = new RestTemplateBuilder().build();;
    }



    /**
     * 주소를 기반으로 위도, 경도를 찾는 메서드
     *
     * address ex )  33 Maddox St, Alexandria NSW 2015, Australia
     * 33 : 건물번호 또는 이름
     * Maddox : 시티이름
     * St : 거리유형
     * Alexandria : 지역이름
     * NSW : 주
     * 2015 : 우편번호
     */
    @Override
    public LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto)  {
        String url = String.format("%s?address=%s&key=%s", GEOCODING_API_URL, toStringAddress(homeAddressDto), API_KEY);
        URI uri = URI.create(url);

        // Google Geocoding API 호출
        String response = restTemplate.getForObject(uri, String.class);

        // JSON 파싱
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject location = jsonObject.getAsJsonArray("results").get(0)
                .getAsJsonObject().getAsJsonObject("geometry")
                .getAsJsonObject("location");


        double lat = location.get("lat").getAsDouble();
        double lng = location.get("lng").getAsDouble();

        // LatLng 객체 생성 후 반환
        return new LatLng(lat, lng);
    }


}
