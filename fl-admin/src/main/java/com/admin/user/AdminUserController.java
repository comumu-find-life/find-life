package com.admin.user;

import com.common.user.response.UserInformationDto;
import com.common.utils.SuccessResponse;
import com.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    /**
     * 모든 회원 정보 조회 API
     */
    @GetMapping()
    public ResponseEntity<?> findAllUsers(){
        List<UserInformationDto> users = userService.findAll();
        SuccessResponse response = new SuccessResponse(true, "모든 회원 정보 조회 성공", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
