package com.batch.user;

import com.batch.util.CookieUtils;
import com.common.user.response.UserInformationDto;
import com.common.user.response.UserProfileResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @GetMapping("me")
    public UserProfileResponse userInfo(HttpServletRequest request) {
        String token = CookieUtils.getTokenFromCookie(request);
        userService.getLoginUserProfile(token);
    }
}
