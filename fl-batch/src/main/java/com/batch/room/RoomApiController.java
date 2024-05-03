package com.batch.room;

import com.service.home.dto.HomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/rooms/Melbourne")
    public List<HomeDto> roomsMelbourne() {
        String city = "Melbourne";
        List<HomeDto> homeDtos = roomApiService.findRoomByCity(city);

        return homeDtos;
    }


    @GetMapping("/hhome")
    public String roomsMelbourned() {

        return "homeDtos";
    }


    @GetMapping("/rooms/{city}")
    public List<HomeDto> rooms(@PathVariable String city) {
        System.out.println("asd");
        List<HomeDto> homeDtos = roomApiService.findRoomByCity(city);

        return homeDtos;
    }
}
