package com.service.home.impl;

import com.common.home.request.HomeAddressGeneratorRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.home.LocationService;
import com.service.home.utils.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.service.home.utils.LocationUtil.toStringAddress;

@Service
public class LocationServiceImpl implements LocationService {

    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String API_KEY = "AIzaSyD1vqgptIQgYusty2ot4ofOabWD6Zpfnf0";
    private static final String ADDRESS_PATTERN = "%s?address=%s&key=%s";

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
    public LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto) throws IllegalAccessException {
        String url = String.format(ADDRESS_PATTERN, GEOCODING_API_URL, toStringAddress(homeAddressDto), API_KEY);
        URI uri = URI.create(url);

        // Google Geocoding API 호출
        String response = restTemplate.getForObject(uri, String.class);
        System.out.println("Response = " + toStringAddress(homeAddressDto));

        // JSON 파싱
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        // jsonObject 출력
        validateAddress(jsonObject);
        try {
            JsonObject location = jsonObject.getAsJsonArray("results").get(0)
                    .getAsJsonObject().getAsJsonObject("geometry")
                    .getAsJsonObject("location");


            double lat = location.get("lat").getAsDouble();
            double lng = location.get("lng").getAsDouble();

            // LatLng 객체 생성 후 반환
            return new LatLng(lat, lng);
        } catch (Exception e) {
            return new LatLng(1.12, 1.12);
        }
    }

    private void validateAddress(JsonObject jsonObject) throws IllegalAccessException {
        if(jsonObject.size() == 0){
            throw new IllegalAccessException("존재하지 않는 주소입니다.");
        }
    }



}
