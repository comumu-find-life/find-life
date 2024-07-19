package com.api.user;

import com.api.security.CustomUserDetails;
import com.api.security.service.JwtService;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationDto;
import com.common.user.response.UserProfileResponse;
import com.service.user.UserService;
import com.common.utils.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @PatchMapping("/{userId}")
    @PreAuthorize("hasAnyRole(ROLE_GETTER, ROLE_GETTER)")
    public ResponseEntity<?> updateUser(@PathVariable Long userId){
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * jwt 로 자신의 userId 조회하기.
     */
    @GetMapping("/me/userId")
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
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.PROFILE_RETRIEVE_SUCCESS, userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user-info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInformationDto> findLoginUser() {
        // 현재 인증된 사용자 정보 가져오기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInformationDto userInfo = userService.findByEmail(email);
        return ResponseEntity.ok(userInfo);
    }


    @GetMapping("/user-id-test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> findLoginUserTest() {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getId());
        System.out.println(userDetails.getAuthorities());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());
        return ResponseEntity.ok(userDetails.getId());
    }

}
