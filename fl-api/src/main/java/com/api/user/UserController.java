package com.api.user;

import com.redis.user.UserRedisService;
import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserSignupRequest;
import com.service.user.dto.UserUpdatePointRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;
    private final UserRedisService userRedisService;

    @PostMapping("/sign-up")
    public ResponseEntity<Long> signUp(@RequestBody UserSignupRequest dto) throws Exception {
        Long userId = userService.signUp(dto);
        return ResponseEntity.ok(userId);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<UserInformationDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    //todo 탈퇴, 수정 메서드 구현 .requestMatchers("/v1/api/user").permitAll()
}
