package com.api.home;

import com.common.home.request.*;
import com.common.home.response.HomeInformationResponse;
import com.common.home.response.HomeOverviewResponse;
import com.common.home.response.HomeOverviewWrapper;
import com.common.utils.SuccessResponse;
import com.service.home.HomeQueryService;
import com.service.home.HomeService;
import com.service.home.LocationService;
import com.service.home.utils.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.api.config.ApiUrlConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeQueryService homeQueryService;
    private final HomeService homeService;
    private final LocationService locationService;

    @PostMapping(HOMES_BASE_URL)
    public ResponseEntity<?> saveHome(@RequestPart final HomeGeneratorRequest homeGeneratorRequest,
                                      @RequestPart("images") final List<MultipartFile> images) throws IllegalAccessException {
        LatLng location = locationService.getLatLngFromAddress(homeGeneratorRequest.getHomeAddress());
        Long homeId = homeService.save(homeGeneratorRequest, images, location);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_POST_SUCCESS, homeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(HOMES_VALIDATE_ADDRESS)
    public ResponseEntity<?> validateAddress(@RequestBody final HomeAddressGeneratorRequest homeAddressGeneratorRequest) throws IllegalAccessException {
        LatLng location = locationService.getLatLngFromAddress(homeAddressGeneratorRequest);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.ADDRESS_VALIDATION_SUCCESS, location);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(HOMES_FIND_BY_USER_ID)
    public ResponseEntity<?> findByUserId(@PathVariable final Long userId) {
        List<HomeOverviewResponse> homes = homeQueryService.findByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.USER_POSTS_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(HOMES_FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable final Long homeId) {
        HomeInformationResponse homeInformationResponse = homeQueryService.findById(homeId);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_RETRIEVE_SUCCESS, homeInformationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(HOMES_BASE_URL)
    public ResponseEntity<?> updateHome(@RequestBody final HomeUpdateRequest homeDto) {
        homeService.update(homeDto);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(HOMES_UPDATE_IMAGE)
    public ResponseEntity<?> updateHomeImage(@PathVariable final Long homeId, @RequestPart("images") final List<MultipartFile> images) {
        homeService.updateHomeImages(homeId, images);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_IMAGE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(HOMES_UPDATE_IMAGE)
    public ResponseEntity<?> deleteHomeImage(@PathVariable final Long homeId, @RequestParam final List<String> imageUrls) {
        homeService.deleteHomeImage(imageUrls);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.HOME_IMAGE_DELETE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(HOMES_CHANGE_STATUS)
    public ResponseEntity<?> changeStatusHome(@PathVariable final Long homeId, @PathVariable final String status) {
        homeService.changeStatus(homeId, status);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_SELL_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(HOMES_FIND_ALL)
    public ResponseEntity<?> findAll() {
        HomeOverviewWrapper allHomes = homeQueryService.findAllHomes();
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.ALL_HOMES_RETRIEVE_SUCCESS, allHomes.getHomes());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(HOMES_FIND_BY_CITY)
    public ResponseEntity<?> findByCity(@RequestParam final String city) {
        List<HomeOverviewResponse> homes = homeQueryService.findByCity(city);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.CITY_HOMES_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(HOMES_FIND_FAVORITE)
    public ResponseEntity<?> findFavoriteHomes(@RequestParam final List<Long> homeIds) {
        List<HomeOverviewResponse> favoriteHomes = homeQueryService.findFavoriteHomes(homeIds);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.FAVORITE_HOMES_RETRIEVE_SUCCESS, favoriteHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(HOMES_DELETE)
    public ResponseEntity<String> delete(@PathVariable final Long homeId) {
        homeService.delete(homeId);
        return ResponseEntity.ok(SuccessHomeMessages.HOME_DELETE_SUCCESS);
    }
}
