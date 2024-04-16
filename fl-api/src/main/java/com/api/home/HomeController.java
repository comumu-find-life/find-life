package com.api.home;

import com.authority.filter.JwtAuthenticationFilter;
import com.core.user.model.User;
import com.service.home.HomeService;
import com.service.home.dto.HomeDto;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;


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
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_PROVIDER')")
    public Long saveHome(HttpServletRequest request, @RequestBody HomeDto homeCreateDto) {
        Optional<User> user = jwtAuthenticationFilter.findByAccessToken(request);
        return homeService.save(user.get(), homeCreateDto);
    }

    @PatchMapping()
    @PreAuthorize("hasRole(ROLE_PROVIDER)")
    public String update(HttpServletRequest httpServletRequest, @RequestBody HomeDto homeDto) {
        Optional<User> user = jwtAuthenticationFilter.findByAccessToken(httpServletRequest);
        homeService.update(user.get(), homeDto);
        return "update";
    }

//    @GetMapping()
//    public HomeDto findHome(HttpServletRequest request, @RequestBody Long homeId){
//        homeService.findAllByPage()
//
//    }
}
