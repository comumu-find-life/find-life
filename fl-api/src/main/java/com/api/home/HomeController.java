package com.api.home;

import com.common.home.request.*;
import com.common.home.response.HomeInformationResponse;
import com.common.home.response.HomeOverviewResponse;
import com.common.utils.SuccessResponse;
import com.service.home.HomeService;
import com.service.home.LocationService;
import com.service.home.utils.LatLng;
import com.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.api.config.ApiUrlConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final LocationService locationService;

    /**
     * 집 게시글 등록 api
     */
    @PostMapping(HOMES_BASE_URL)
    public ResponseEntity<?> saveHome(@RequestPart HomeGeneratorRequest homeGeneratorRequest,
                                      @RequestPart("images") List<MultipartFile> images) throws IllegalAccessException {
        LatLng location = locationService.getLatLngFromAddress(homeGeneratorRequest.getHomeAddress());
        Long homeId = homeService.save(homeGeneratorRequest, images, location);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_POST_SUCCESS, homeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 주소 검색 (검증) api
     */
    @PostMapping(HOMES_VALIDATE_ADDRESS)
    public ResponseEntity<?> validateAddress(@RequestBody HomeAddressGeneratorRequest homeAddressGeneratorRequest) throws IllegalAccessException {
        LatLng location = locationService.getLatLngFromAddress(homeAddressGeneratorRequest);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.ADDRESS_VALIDATION_SUCCESS, location);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자(본인, 다른 사용자) 집 게시글 모두 조회 api
     */
    @GetMapping(HOMES_FIND_BY_USER_ID)
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        List<HomeOverviewResponse> homes = homeService.findByUserId(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.USER_POSTS_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 id 로 단일 조회 api
     */
    @GetMapping(HOMES_FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable Long homeId) {
        HomeInformationResponse homeInformationResponse = homeService.findById(homeId);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_RETRIEVE_SUCCESS, homeInformationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 정보 수정 api
     */
    @PatchMapping(HOMES_BASE_URL)
    public ResponseEntity<?> updateHome(@RequestBody HomeUpdateRequest homeDto) {
        homeService.update(homeDto);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 이미지 추가 등록 api
     */
    @PatchMapping(HOMES_UPDATE_IMAGE)
    public ResponseEntity<?> updateHomeImage(@PathVariable Long homeId, @RequestPart("images") List<MultipartFile> images) {
        // 이미지 처리 로직 추가
        homeService.updateHomeImages(homeId, images);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_IMAGE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 이미지 삭제 API
     * ex) DELETE /v1/api/homes/{homeId}/image?imageIds=1,2,3
     */
    @DeleteMapping(HOMES_UPDATE_IMAGE)
    public ResponseEntity<?> deleteHomeImage(@PathVariable Long homeId, @RequestParam List<String> imageUrls) {
        homeService.deleteHomeImage(homeId, imageUrls);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.HOME_IMAGE_DELETE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 상태 변경 API (판매중, 판매 완료, 판매 정지)
     */
    @PatchMapping(HOMES_CHANGE_STATUS)
    public ResponseEntity<?> changeStatusHome(@PathVariable Long homeId, @PathVariable String status) {
        homeService.changeStatus(homeId, status);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.HOME_SELL_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 집 게시글 모두 조회 api
     */
    @GetMapping(HOMES_FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<HomeOverviewResponse> allHomes = homeService.findAllHomes();
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.ALL_HOMES_RETRIEVE_SUCCESS, allHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 게시글 페이징 조회 api
     * ex) /homes?page=0&size=10
     */
    @GetMapping
    public ResponseEntity<?> findByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<HomeOverviewResponse> allByPage = homeService.findAllByPage(page, size);
        SuccessResponse response = new SuccessResponse(true, SuccessHomeMessages.PAGE_HOMES_RETRIEVE_SUCCESS, allByPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * city 이름으로 집 조회 api
     */
    @GetMapping(HOMES_FIND_BY_CITY)
    public ResponseEntity<?> findByCity(@RequestParam String city) {
        List<HomeOverviewResponse> homes = homeService.findByCity(city);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.CITY_HOMES_RETRIEVE_SUCCESS, homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 찜 목록 조회 api
     */
    @GetMapping(HOMES_FIND_FAVORITE)
    public ResponseEntity<?> findFavoriteHomes(@RequestParam List<Long> homeIds) {
        List<HomeOverviewResponse> favoriteHomes = homeService.findFavoriteHomes(homeIds);
        SuccessResponse<Object> response = new SuccessResponse<>(true, SuccessHomeMessages.FAVORITE_HOMES_RETRIEVE_SUCCESS, favoriteHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 집 게시글 삭제 api
     */
    @DeleteMapping(HOMES_DELETE)
    public ResponseEntity<String> delete(@PathVariable Long homeId) {
        homeService.delete(homeId);
        return ResponseEntity.ok(SuccessHomeMessages.HOME_DELETE_SUCCESS);
    }


}
