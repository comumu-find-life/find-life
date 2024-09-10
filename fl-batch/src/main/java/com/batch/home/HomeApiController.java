package com.batch.home;

import com.batch.util.CookieUtils;
import com.common.home.request.HomeGeneratorRequest;
import com.common.home.response.HomeInformationResponse;
import com.common.home.response.HomeOverviewResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeApiController {

    private final HomeApiService homeApiService;

    @GetMapping("/homes")
    public List<HomeOverviewResponse> homes() {
        List<HomeOverviewResponse> homeDtos = homeApiService.findRooms();

        return homeDtos;
    }

    @GetMapping("/homes/{city}")
    public List<HomeOverviewResponse> findRoomsByCity(@PathVariable String city) {
        List<HomeOverviewResponse> homeDtos = homeApiService.findRoomByCity(city);

        return homeDtos;
    }

    @GetMapping("/home/{homeId}")
    public HomeInformationResponse roomDetail(@PathVariable Long homeId) {
        HomeInformationResponse homeDto = homeApiService.findRoomByRoomId(homeId);

//        log.info(homeDto.getHomeInfo().getBathroomType());

        return homeDto;
    }


    @PostMapping("/home/new")
    public String homeNew(@ModelAttribute HomeGeneratorRequest homeRequest,
                          @RequestParam("images") List<MultipartFile> images, HttpServletRequest request) throws IOException {

        // 인증정보 받아오기
        String token = CookieUtils.getTokenFromCookie(request);
        homeApiService.addHomePost(homeRequest, images, token);

        return "redirect:/";
    }

}
