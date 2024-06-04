package com.api.user;

import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserProfileRequest;
import com.service.user.dto.UserSignupRequest;
import com.service.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserSignupRequest dto) throws Exception {
        Long userId = userService.signUp(dto);
        SuccessResponse response = new SuccessResponse(true, "회원가입 성공", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping()
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<UserInformationDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // 사용자가 다른 사용자 프로필 정보를 조회할때 사용 하는 api
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> getUserProfile(@RequestParam Long id){
        UserProfileRequest userProfile = userService.getUserProfile(id);
        SuccessResponse response = new SuccessResponse(true, "사용자 프로필 조회 성공", userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user-info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInformationDto> findLoginUser() {
//        return ResponseEntity.ok(userService.findById(id));
        // 현재 인증된 사용자 정보 가져오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationDto userInfo = userService.findByEmail(email);

        return ResponseEntity.ok(userInfo);
    }

    //todo 탈퇴, 수정 메서드 구현 .requestMatchers("/v1/api/user").permitAll()
}
