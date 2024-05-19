package com.batch.room;

import com.service.home.dto.SimpleHomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomApiService roomApiService;

    @GetMapping("/rooms/{city}")
    public List<SimpleHomeDto> rooms(@PathVariable String city) {
        List<SimpleHomeDto> homeDtos = roomApiService.findRoomByCity(city);

        return homeDtos;
    }

}
