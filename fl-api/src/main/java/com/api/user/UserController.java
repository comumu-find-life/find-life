package com.api.user;

import com.api.security.service.JwtService;
import com.service.user.UserService;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserProfileResponse;
import com.service.user.dto.UserSignupRequest;
import com.service.utils.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 회원가입 api
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestPart UserSignupRequest userSignupRequest,
                                    @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        Long userId = userService.signUp(userSignupRequest, image);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.SIGN_UP_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 본인 프로필 조회 api
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> findById(@PathVariable Long userId) {
        UserInformationDto byId = userService.findById(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_RETRIEVE_SUCCESS, byId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * jwt 로 자신의 userId 조회하기.
     */

    @GetMapping("/me/userId")
    @PreAuthorize("hasAnyRole('ROLE_GETTER')")
    public ResponseEntity<?> getMyUserId(HttpServletRequest request) {
        String accessToken  = jwtService.extractAccessToken(request).get();
        String email = jwtService.extractEmail(accessToken).get();
        Long userId = userService.findByEmail(email).getId();
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_USER_ID_RETRIEVE_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * 다른 사용자가 프로필을 조회하는 api
     */
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.PROFILE_RETRIEVE_SUCCESS, userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
