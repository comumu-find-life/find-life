package com.api.home;

import com.authority.filter.JwtAuthenticationFilter;
import com.core.user.model.User;
import com.service.home.HomeService;
import com.service.home.dto.HomeDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/home")
public class HomeController {
    private final HomeService homeService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //게시글 저장 api
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    public Long saveHome(HttpServletRequest request, @RequestBody HomeDto homeCreateDto) {
        Optional<User> user = jwtAuthenticationFilter.findByAccessToken(request);
        return homeService.save(user.get(), homeCreateDto);
    }

    //id 로 home 조회
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_PROVIDER', 'ROLE_GETTER)")
    public ResponseEntity<HomeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(homeService.findById(id));
    }

    //home 수정
    @PatchMapping()
    @PreAuthorize("hasRole(ROLE_PROVIDER)")
    public ResponseEntity<String> update(HttpServletRequest httpServletRequest, @RequestBody HomeDto homeDto) {
        Optional<User> user = jwtAuthenticationFilter.findByAccessToken(httpServletRequest);
        homeService.update(user.get(), homeDto);
        return ResponseEntity.ok("update!");
    }

    @DeleteMapping()
    @PreAuthorize("hasRole(ROLE_PROVIDER)")
    public ResponseEntity<String> delete(@PathVariable Long id){
        homeService.delete(id);
        return ResponseEntity.ok("delete!");
    }

//    @GetMapping()
//    public HomeDto findHome(HttpServletRequest request, @RequestBody Long homeId){
//        homeService.findAllByPage()
//
//    }
}
