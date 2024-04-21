package com.api.user;

import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserSignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public String signUp(@RequestBody UserSignupRequest dto) throws Exception {
        userService.signUp(dto);
        return "success";
    }

    @GetMapping()
    public UserInformationDto findById(HttpServletRequest request, @RequestBody Long id){
        return userService.findById(id);
    }

    @DeleteMapping()
    public String delete(Long id){
        userService.delete(id);
        return "delete!";
    }

//    @PatchMapping("/")
//    public String update(HttpServletRequest request, @RequestBody ) {
//
//    }
}
