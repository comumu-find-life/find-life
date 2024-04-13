package com.api.user;

import com.service.user.UserService;
import com.service.user.dto.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    public String test(){
        return "TEST";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignupRequest dto) throws Exception {
        userService.signUp(dto);
        return "success";
    }
}
