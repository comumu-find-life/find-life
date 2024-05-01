package com.batch.room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class RoomApiController {

    @GetMapping("/rooms")
    public String rooms() {

         ResponseEntity<UserInformationDto>

        return "Melbourne";
    }
}
