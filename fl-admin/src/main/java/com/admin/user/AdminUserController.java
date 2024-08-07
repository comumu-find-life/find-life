package com.admin.user;

import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationByAdminResponse;
import com.common.user.response.UserInformationResponse;
import com.common.utils.SuccessResponse;
import com.service.user.UserAdminService;
import com.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/admin/users")
public class AdminUserController {

    private final UserAdminService userService;
    private final UserService userService2;

    /**
     * 계정 생성 API (추후 삭제) - done
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest userSignupRequest){
        Long userId = userService.signUpAdmin(userSignupRequest);
        SuccessResponse response = new SuccessResponse(true, "admin 계정 생성 완료", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 모든 회원 정보 조회 API - done
     */
    @GetMapping()
    public ResponseEntity<?> findAllUsers() {
        List<UserInformationResponse> users = userService.findAll();
        SuccessResponse response = new SuccessResponse(true, "모든 회원 정보 조회 성공", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 회원 단일 조회 API - done
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> findById(@PathVariable Long userId) {
        UserInformationByAdminResponse byId = userService.findById(userId);
        SuccessResponse response = new SuccessResponse(true, "회원 단일 조회 성공", byId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 회원 계정 정지 API - done
     */
    @PatchMapping("/{userId}/inactive")
    public ResponseEntity<?> setUserInactive(@PathVariable Long userId){
        userService.setUserInactive(userId);
        SuccessResponse response = new SuccessResponse(true, "회원 비활성화 성공", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
