package com.api.home;

import com.api.dto.SuccessResponse;
import com.service.home.HomeService;
import com.service.home.LocationService;
import com.service.home.dto.request.HomeGeneratorRequest;
import com.service.home.dto.LatLng;
import com.service.home.dto.SimpleHomeDto;
import com.service.home.dto.response.HomeInformationResponse;
import lombok.RequiredArgsConstructor;


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

//    @GetMapping("/home")
//    public ResponseEntity<?> findById(@PathVariable Long homeId){
//
//    }


    //public ResponseEntity<SimpleHomeDto>
    @DeleteMapping("/home")
    @PreAuthorize("hasRole(ROLE_PROVIDER)")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        homeService.delete(id);
        return ResponseEntity.ok("delete!");
    }

    // ex) /homes?page=1&size=10
    @GetMapping("/homes")
    public ResponseEntity<List<SimpleHomeDto>> findByPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(homeService.findAllByPage(page, size));
    }

    // ex) /homes?page=1&size=10
    @GetMapping("/homes/{city}")
    public ResponseEntity<List<SimpleHomeDto>> findByCity(@PathVariable String city, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(homeService.findByCity(city, page, size));
    }

    @GetMapping("/homes/favorite")
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<List<SimpleHomeDto>> findFavoriteHomes(@RequestBody List<Long> homeIds) {
        return ResponseEntity.ok(homeService.findFavoriteHomes(homeIds));
    }


}
