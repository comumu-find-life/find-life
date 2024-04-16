package com.api.user;

import com.service.user.UserService;
import com.service.user.dto.UserSignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignupRequest dto) throws Exception {
        userService.signUp(dto);
        return "success";
    }

//    @PatchMapping("/")
//    public String update(HttpServletRequest request, @RequestBody ) {
//
//    }
}
