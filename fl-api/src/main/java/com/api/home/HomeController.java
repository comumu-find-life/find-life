package com.api.home;

import com.service.home.HomeService;
import com.service.home.LocationService;
import com.service.home.dto.request.HomeAddressGeneratorRequest;
import com.service.home.dto.response.HomeOverviewResponse;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.geocoding.LatLng;
import com.service.home.dto.request.HomeUpdateRequest;
import com.service.home.dto.response.HomeInformationResponse;
import com.service.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/homes")
public class HomeController {
    private final HomeService homeService;
    private final LocationService locationService;

    //게시글 저장 api
    @PostMapping
    public ResponseEntity<?> saveHome(@RequestPart HomeGeneratorRequest homeCreateDto,
                                      @RequestPart("images") List<MultipartFile> images) throws IOException, IllegalAccessException {
        //주소 -> 위도, 경도 변환
        LatLng location = locationService.getLatLngFromAddress(homeCreateDto.getHomeAddress());
        Long homeId = homeService.save(homeCreateDto, images, location);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_POST_SUCCESS, homeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 주소 유효성 검사를 위한 LatLng 반환
    @PostMapping("/address/validate")
    public ResponseEntity<?> validateAddress(@RequestBody HomeAddressGeneratorRequest homeAddressGeneratorRequest) throws IllegalAccessException {
        LatLng location = locationService.getLatLngFromAddress(homeAddressGeneratorRequest);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.ADDRESS_VALIDATION_SUCCESS, location);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        List<HomeOverviewResponse> homes = homeService.findByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.USER_POSTS_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{homeId}")
    public ResponseEntity<?> findById(@PathVariable Long homeId) {
        HomeInformationResponse homeInformationResponse = homeService.findById(homeId);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_RETRIEVE_SUCCESS, homeInformationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<?> updateHome(@RequestBody HomeUpdateRequest homeDto) {
        homeService.update(homeDto);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/image")
    public ResponseEntity<?> updateHomeImage(@RequestParam("images") List<MultipartFile> images) {
        // 이미지 처리 로직 추가
        for (MultipartFile image : images) {
            // 예: 이미지를 저장하거나 검증하는 코드 추가
            System.out.println("Received image: " + image.getOriginalFilename());
        }
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_IMAGE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sellHome(@RequestParam Long homeId) {
        homeService.changeStatus(homeId);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_SELL_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 맵 화면에서 사용할 예정
    @GetMapping("/overview")
    public ResponseEntity<?> findAll() {
        List<HomeOverviewResponse> allHomes = homeService.findAllHomes();
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.ALL_HOMES_RETRIEVE_SUCCESS, allHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{homeId}")
    @PreAuthorize("hasRole(ROLE_PROVIDER)")
    public ResponseEntity<String> delete(@PathVariable Long homeId) {
        homeService.delete(homeId);
        return ResponseEntity.ok(SuccessHomeMessages.HOME_DELETE_SUCCESS);
    }

    /**
     * 집 게시글 리스트 화면에서 사용
     * ex) /homes?page=1&size=10
     */
    @GetMapping()
    public ResponseEntity<List<HomeOverviewResponse>> findByPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(homeService.findAllByPage(page, size));
    }

    /**
     * city 이름으로 검색
     */
    @GetMapping("/city")
    public ResponseEntity<?> findByCity(@RequestParam String city) {
        List<HomeOverviewResponse> homes = homeService.findByCity(city);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.CITY_HOMES_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/favorite")
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> findFavoriteHomes(@RequestParam List<Long> homeIds) {
        List<HomeOverviewResponse> favoriteHomes = homeService.findFavoriteHomes(homeIds);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.FAVORITE_HOMES_RETRIEVE_SUCCESS, favoriteHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
