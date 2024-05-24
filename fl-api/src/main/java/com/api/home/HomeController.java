package com.api.home;

import com.api.dto.SuccessResponse;
import com.service.home.HomeService;
import com.service.home.LocationService;
import com.service.home.dto.HomeOverviewResponse;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.LatLng;
import com.service.home.dto.response.HomeInformationResponse;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class HomeController {
    private final HomeService homeService;
    private final LocationService locationService;

    //게시글 저장 api
    @PostMapping("/home")
    public ResponseEntity<?> saveHome(@RequestBody HomeGeneratorRequest homeCreateDto) throws IOException {
        //주소 -> 위도, 경도 변환
        LatLng location = locationService.getLatLngFromAddress(homeCreateDto.getHomeAddress());
        Long homeId = homeService.save(homeCreateDto, location);
        SuccessResponse response = new SuccessResponse(true, "집 게시글 등록 성공", homeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<?> findById(@RequestParam Long homeId){
        HomeInformationResponse homeInformationResponse = homeService.findById(homeId);
        SuccessResponse response = new SuccessResponse(true, "집 게시글 조회 성공", homeInformationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/homes/overview")
    public ResponseEntity<?> findAll(){
        List<HomeOverviewResponse> allHomes = homeService.findAllHomes();
        SuccessResponse response = new SuccessResponse(true, "모든 집 정보 조회 성공", allHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //public ResponseEntity<HomeOverviewResponse>
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
    public ResponseEntity<?> findFavoriteHomes(@RequestBody List<Long> homeIds) {
        List<HomeOverviewResponse> favoriteHomes = homeService.findFavoriteHomes(homeIds);

        SuccessResponse<Object> response = new SuccessResponse<>(true, "찜 목록 집 조회 성공", favoriteHomes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
