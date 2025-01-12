package com.api.user;

import com.api.security.service.JwtService;
import com.common.user.request.UserAccountRequest;
import com.common.user.request.UserProfileUpdateRequest;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserAccountResponse;
import com.common.user.response.UserInformationResponse;
import com.common.user.response.UserProfileResponse;
import com.service.user.UserService;
import com.common.utils.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.api.config.ApiUrlConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(USERS_SIGN_UP_EMAIL)
    public ResponseEntity<?> signUp(@RequestPart final  UserSignupRequest userSignupRequest,
                                    @RequestPart(value = "image", required = false) final MultipartFile image) throws Exception {
        Long userId = userService.signUp(userSignupRequest,passwordEncoder.encode(userSignupRequest.getPassword()), image);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.SIGN_UP_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(USERS_SIGN_UP_GOOGLE)
    public ResponseEntity<?> signUpGoogle(@RequestPart final UserSignupRequest userSignupRequest,
                                    @RequestPart(value = "image", required = false) final MultipartFile image) throws Exception {
        Long userId = userService.signUp(userSignupRequest, "",image);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.SIGN_UP_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_CHECK_DUPLICATE_EMAIL)
    public ResponseEntity<?> validateDuplicateEmail(@PathVariable final String email){
        boolean result = userService.validateDuplicateEmail(email);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.EMAIL_DUPLICATE_VERIFICATION_SUCCESS, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(USERS_FCM_TOKEN_REGISTER)
    public ResponseEntity<?> registerFcmToken(@RequestParam final String fcmToken){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateFcmToken(email, fcmToken);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.FCM_TOKEN_UPDATE, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_FIND_BY_ID)
    public ResponseEntity<?> findById(@PathVariable final Long userId) {
        UserInformationResponse byId = userService.findById(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_RETRIEVE_SUCCESS, byId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(USERS_UPDATE)
    public ResponseEntity<?> updateUser(@RequestBody final UserProfileUpdateRequest userProfileUpdateRequest){
        userService.update(userProfileUpdateRequest);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(USERS_IMAGE_UPDATE)
    public ResponseEntity<?> updateUserImage(@PathVariable final Long userId, @RequestPart(value = "image", required = false) final MultipartFile image) {
        userService.updateImage(userId, image);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_PROFILE_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_GET_MY_USER_ID)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyUserId(final HttpServletRequest request) {
        String accessToken  = jwtService.extractAccessToken(request).get();
        String email = jwtService.extractEmail(accessToken).get();
        Long userId = userService.findByEmail(email).getId();
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_USER_ID_RETRIEVE_SUCCESS, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_GET_PROFILE)
    public ResponseEntity<?> getUserProfile(@PathVariable final Long userId) {
        UserProfileResponse userProfile = userService.getUserProfile(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.PROFILE_RETRIEVE_SUCCESS, userProfile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(USERS_FIND_BY_ID)
    public ResponseEntity<?> deleteAccount(@PathVariable final Long userId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.delete(email, userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.DELETE_ACCOUNT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS_RE_LOGIN)
    public ResponseEntity<?> reLogin(){
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.SIGN_UP_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
