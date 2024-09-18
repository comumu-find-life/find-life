package com.api.user;

import com.api.security.service.JwtService;
import com.common.user.request.UserProfileUpdateRequest;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.redis.user.service.UserRedisService;
import com.service.user.UserService;
import com.common.utils.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.api.config.ApiUrlConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;
    private final UserRedisService userRedisService;
    private final JwtService jwtService;

    /**
     * 회원가입 api
     */
    @PostMapping(USERS_SIGN_UP)
    public ResponseEntity<?> signUp(@RequestPart UserSignupRequest userSignupRequest,
                                    @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        Long userId = userService.signUp(userSignupRequest, image);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.SIGN_UP_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 이메일 중복 확인 API
     */
    @GetMapping(USERS_CHECK_DUPLICATE_EMAIL)
    public ResponseEntity<?> validateDuplicateEmail(String email){
        boolean result = userService.validateDuplicateEmail(email);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.EMAIL_DUPLICATE_VERIFICATION_SUCCESS, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 본인 프로필 조회 api
     */
    @GetMapping(USERS_FIND_BY_ID)
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> findById(@PathVariable Long userId) {
        UserInformationResponse byId = userService.findById(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_RETRIEVE_SUCCESS, byId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 프로필 수정 API
     */
    @PatchMapping(USERS_UPDATE)
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> updateUser(@RequestBody UserProfileUpdateRequest userProfileUpdateRequest){
        userService.update(userProfileUpdateRequest);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 프로필 사진 수정 API
     */
    @PatchMapping(USERS_IMAGE_UPDATE)
    public ResponseEntity<?> updateUserImage(@PathVariable Long userId, @RequestPart(value = "image", required = false) MultipartFile image) {
        userService.updateImage(userId, image);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * jwt 로 자신의 userId 조회하기.
     */
    @GetMapping(USERS_GET_MY_USER_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyUserId(HttpServletRequest request) {
        String accessToken  = jwtService.extractAccessToken(request).get();
        String email = jwtService.extractEmail(accessToken).get();
        // String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userService.findByEmail(email).getId();
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_USER_ID_RETRIEVE_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 다른 사용자가 프로필을 조회하는 api
     */
    @GetMapping(USERS_GET_PROFILE)
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.PROFILE_RETRIEVE_SUCCESS, userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_FIND_LOGIN_USER)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInformationResponse> findLoginUser() {
        // 현재 인증된 사용자 정보 가져오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationResponse userInfo = userService.findByEmail(email);
        return ResponseEntity.ok(userInfo);
    }


}
