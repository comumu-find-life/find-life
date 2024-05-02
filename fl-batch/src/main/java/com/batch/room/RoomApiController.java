package com.batch.room;

import com.service.home.dto.HomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController("/api")
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomApiService roomApiService;

    @GetMapping("/rooms/{city}")
    public List<HomeDto> rooms(@PathVariable String city) {

        List<HomeDto> homeDtos = roomApiService.findRoomByCity(city);

        return homeDtos;
    }
}
