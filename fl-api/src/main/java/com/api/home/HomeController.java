package com.api.home;

import com.service.home.HomeService;
import com.service.home.LocationService;
import com.service.home.dto.HomeDto;
import com.service.home.dto.LatLng;
import com.service.home.dto.SimpleHomeDto;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class HomeController {
    private final HomeService homeService;
    private final LocationService locationService;

    //게시글 저장 api
    @PostMapping("/home")
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    public ResponseEntity<Long> saveHome(@RequestBody HomeDto homeCreateDto) throws IOException {
        LatLng location = locationService.getLatLngFromAddress(homeCreateDto.getHomeAddress());
        return ResponseEntity.ok(homeService.save(homeCreateDto, location));
    }

    //id 로 home 조회
    @GetMapping("/home")
    public ResponseEntity<HomeDto> findById(@RequestParam Long homeId) {
        return ResponseEntity.ok(homeService.findById(homeId));
    }

    //home 수정
    @PatchMapping("/home/{homeId}")
    @PreAuthorize("hasRole(ROLE_PROVIDER)")
    public ResponseEntity<String> update(@RequestBody HomeDto homeDto) {
        homeService.update(homeDto);
        return ResponseEntity.ok("update!");
    }

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
