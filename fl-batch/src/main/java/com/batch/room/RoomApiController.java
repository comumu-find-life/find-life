package com.batch.room;

import com.service.home.dto.response.HomeOverviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomApiService roomApiService;

    @GetMapping("/rooms/{city}")
    public List<HomeOverviewResponse> rooms(@PathVariable String city) {
        List<HomeOverviewResponse> homeDtos = roomApiService.findRoomByCity(city);

        return homeDtos;
    }

    @GetMapping("/room/{roomId}")
    public HomeOverviewResponse roomDetail(@PathVariable Long roomId) {
        HomeOverviewResponse homeDto = roomApiService.findRoomByRoomId(roomId);

        return homeDto;
    }

}
