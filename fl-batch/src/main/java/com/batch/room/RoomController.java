package com.batch.room;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomController {

    @GetMapping("/rooms")
    public String roomList(@RequestParam String city) {

        return "redirect:/rooms/" + city;
    }

    @GetMapping("/rooms/{city}")
    public String roomListPage(@PathVariable String city, Model model) {
        model.addAttribute("city", city);
        return "room/roomList";
    }

    @GetMapping("/room/{id}")
    public String roomPage(@PathVariable Long id, Model model) {
        model.addAttribute("roomId", id);
        return "room/room";
    }
//
//    @GetMapping("/test")
//    public String test() {
//        return "main/mainPage";
//    }
}
