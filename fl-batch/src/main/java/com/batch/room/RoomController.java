package com.batch.room;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    @GetMapping("/rooms/melbourne")
    public String roomList() {
        return "room/roomList";
    }
}
