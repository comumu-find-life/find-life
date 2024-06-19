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
@RequestMapping("/v1/api")
public class HomeController {
    private final HomeService homeService;
    private final LocationService locationService;

    //게시글 저장 api
    @PostMapping("/home")
    public ResponseEntity<?> saveHome(@RequestPart HomeGeneratorRequest homeCreateDto,
                                      @RequestPart("images") List<MultipartFile> images) throws IOException {
        //주소 -> 위도, 경도 변환
        LatLng location = locationService.getLatLngFromAddress(homeCreateDto.getHomeAddress());
        Long homeId = homeService.save(homeCreateDto, images, location);
        SuccessResponse response = new SuccessResponse(true, "집 게시글 등록 성공", homeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 주소 유효성 검사를 위한 LatLng 반환
    @PostMapping("/home/address/validate")
    public ResponseEntity<?> validateAddress(@RequestBody HomeAddressGeneratorRequest homeAddressGeneratorRequest) {
        LatLng location = locationService.getLatLngFromAddress(homeAddressGeneratorRequest);

        SuccessResponse response = new SuccessResponse(true, "주소 반환 성공", location);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<?> findByUserIdx(@RequestParam Long userIdx){
        List<HomeOverviewResponse> homes = homeService.findByUserIdx(userIdx);

        SuccessResponse response = new SuccessResponse(true, "내 게시글 조회 성공", homes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/home")
    public ResponseEntity<?> findById(@RequestParam Long homeId) {
        HomeInformationResponse homeInformationResponse = homeService.findById(homeId);
        SuccessResponse response = new SuccessResponse(true, "집 게시글 조회 성공", homeInformationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("/home")
    public ResponseEntity<?> updateHome(@RequestBody HomeUpdateRequest homeDto) {
        homeService.update(homeDto);
        SuccessResponse response = new SuccessResponse(true, "집 게시글 수정 성공", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/home/image")
    public ResponseEntity<?> updateHomeImage(@RequestParam("images") List<MultipartFile> images) {
        // 이미지 처리 로직 추가
        for (MultipartFile image : images) {
            // 예: 이미지를 저장하거나 검증하는 코드 추가
            System.out.println("Received image: " + image.getOriginalFilename());
        }
        SuccessResponse response = new SuccessResponse(true, "집 게시글 사진 수정 성공", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/home/sell")
    public ResponseEntity<?> sellHome(@RequestParam Long homeId) {
        homeService.changeStatus(homeId);
        SuccessResponse response = new SuccessResponse(true, "집 판매 완료", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/homes/overview")
    public ResponseEntity<?> findAll() {
        List<HomeOverviewResponse> allHomes = homeService.findAllHomes();
        SuccessResponse response = new SuccessResponse(true, "모든 집 정보 조회 성공", allHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/home")
    @PreAuthorize("hasRole(ROLE_PROVIDER)")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        homeService.delete(id);
        return ResponseEntity.ok("delete!");
    }

    // ex) /homes?page=1&size=10
    @GetMapping("/homes")
    public ResponseEntity<List<HomeOverviewResponse>> findByPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(homeService.findAllByPage(page, size));
    }

    // ex) /homes?page=1&size=10
    @GetMapping("/homes/{city}")
    public ResponseEntity<?> findByCity(@PathVariable String city, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<HomeOverviewResponse> homes = homeService.findByCity(city, page, size);
        SuccessResponse<Object> response = new SuccessResponse<>(true, "city 이름으로 조회 성공", homes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/homes/favorite")
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> findFavoriteHomes(@RequestParam List<Long> homeIds) {
        List<HomeOverviewResponse> favoriteHomes = homeService.findFavoriteHomes(homeIds);

        SuccessResponse<Object> response = new SuccessResponse<>(true, "찜 목록 집 조회 성공", favoriteHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
