package com.api.home;

import com.authority.filter.JwtAuthenticationFilter;
import com.core.user.model.User;
import com.service.home.HomeService;
import com.service.home.dto.HomeCreateDto;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Long saveHome(HttpServletRequest request, @RequestBody HomeCreateDto homeCreateDto) {
        Optional<User> user = jwtAuthenticationFilter.findByAccessToken(request);
        return homeService.save(user.get(), homeCreateDto);
    }
}
