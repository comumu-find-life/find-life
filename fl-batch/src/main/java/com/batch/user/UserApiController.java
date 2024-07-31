package com.batch.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

//    @GetMapping("me")
//    public void userInfo(HttpServletRequest request) {
//        log.info(request.getAttribute("userId") + "");
//        log.info(request.getAttribute("accessToken") + "");
//
////        String token = CookieUtils.getTokenFromCookie(request);
////        return userService.getLoginUserProfile(token);
//    }
}
