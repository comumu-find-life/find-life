package com.api.user;

import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserProfileResponse;
import com.service.user.dto.UserSignupRequest;
import com.service.utils.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestPart UserSignupRequest userSignupRequest,
                                    @RequestPart("image") MultipartFile image) throws Exception {
        Long userId = userService.signUp(userSignupRequest, image);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.SIGN_UP_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> findById(@PathVariable Long userId) {
        UserInformationDto byId = userService.findById(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_RETRIEVE_SUCCESS, byId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 사용자가 다른 사용자 프로필 정보를 조회할 때 사용하는 API
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.PROFILE_RETRIEVE_SUCCESS, userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
