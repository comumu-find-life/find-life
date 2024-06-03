package com.api.user;

import com.api.dto.SuccessResponse;
import com.service.file.FileService;
import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserProfileRequest;
import com.service.user.dto.UserSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/user")
public class UserController {

    private final FileService fileService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestPart UserSignupRequest dto,
                                    @RequestPart("image") MultipartFile image
    ) throws Exception {
        Long userId = userService.signUp(dto, image);
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
    public ResponseEntity<?> getUserProfile(@RequestParam Long id) {
        UserProfileRequest userProfile = userService.getUserProfile(id);
        SuccessResponse response = new SuccessResponse(true, "사용자 프로필 조회 성공", userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //todo 탈퇴, 수정 메서드 구현 .requestMatchers("/v1/api/user").permitAll()
}
