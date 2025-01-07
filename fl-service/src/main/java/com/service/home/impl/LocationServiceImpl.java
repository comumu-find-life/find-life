package com.service.home.impl;

import com.common.home.request.HomeAddressGeneratorRequest;
import com.core.exception.GoogleLocationException;
import com.core.exception.InvalidDataException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.home.LocationService;
import com.service.home.utils.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.service.home.utils.LocationUtil.toStringAddress;

@Service
public class LocationServiceImpl implements LocationService {

    @Value("${map.api-url}")
    private String GEOCODING_API_URL;

    @Value("${map.api-key}")
    private String API_KEY;

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
    public LatLng getLatLngFromAddress(HomeAddressGeneratorRequest homeAddressDto)  {
        String url = String.format(ADDRESS_PATTERN, GEOCODING_API_URL, toStringAddress(homeAddressDto), API_KEY);
        URI uri = URI.create(url);
        String response = restTemplate.getForObject(uri, String.class);
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        validateAddress(jsonObject);
        try {
            JsonObject location = jsonObject.getAsJsonArray("results").get(0)
                    .getAsJsonObject().getAsJsonObject("geometry")
                    .getAsJsonObject("location");
            double lat = location.get("lat").getAsDouble();
            double lng = location.get("lng").getAsDouble();

            return new LatLng(lat, lng);
        } catch (Exception e) {
            throw new GoogleLocationException("주소 조회 실패");
        }
    }

    private void validateAddress(JsonObject jsonObject)  {
        if(jsonObject.size() == 0){
            throw new InvalidDataException("존재하지 않는 주소입니다.");
        }
    }



}
