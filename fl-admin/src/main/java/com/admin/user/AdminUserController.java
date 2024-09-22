package com.admin.user;

import com.admin.config.AdminApiUrls;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationByAdminResponse;
import com.common.user.response.UserInformationResponse;
import com.common.utils.SuccessResponse;
import com.service.user.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.admin.config.AdminApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping()  // 상수로 URL 지정
public class AdminUserController {

    private final UserAdminService userService;

    @PostMapping(USER_SIGN_UP)  // 상수로 URL 지정
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest userSignupRequest){
        Long userId = userService.signUpAdmin(userSignupRequest);
        SuccessResponse response = new SuccessResponse(true, "admin 계정 생성 완료", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(USERS)
    public ResponseEntity<?> findAllUsers() {
        List<UserInformationResponse> users = userService.findAll();
        SuccessResponse response = new SuccessResponse(true, "모든 회원 정보 조회 성공", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findById(@PathVariable Long userId) {
        UserInformationByAdminResponse byId = userService.findById(userId);
        SuccessResponse response = new SuccessResponse(true, "회원 단일 조회 성공", byId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 회원 비 활성화 API
     */
    @PatchMapping(USER_INACTIVE)
    public ResponseEntity<?> setUserInactive(@PathVariable Long userId){
        userService.setUserInactive(userId);
        SuccessResponse response = new SuccessResponse(true, "회원 비 활성화 성공", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}