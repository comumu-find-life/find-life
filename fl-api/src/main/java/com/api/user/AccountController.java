package com.api.user;

import com.common.user.request.UserAccountRequest;
import com.common.user.response.UserAccountResponse;
import com.common.utils.SuccessResponse;
import com.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.api.config.ApiUrlConstants.USER_ACCOUNT_EXIST_URL;
import static com.api.config.ApiUrlConstants.USER_ACCOUNT_REGISTER_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AccountController {

    private final UserService userService;
    /**
     * 계좌 정보 등록 api
     */
    @PostMapping(USER_ACCOUNT_REGISTER_URL)
    public ResponseEntity<?> registerAccount(@RequestBody UserAccountRequest userAccountRequest, @PathVariable Long userId){
        userService.setUserAccount(userAccountRequest, userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_ACCOUNT_REGISTER_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 계좌 정보 조회 api
     */
    @GetMapping(USER_ACCOUNT_REGISTER_URL)
    public ResponseEntity<?> findUserAccount(@PathVariable Long userId){
        UserAccountResponse userAccountById = userService.findUserAccountById(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_ACCOUNT_FIND_SUCCESS, userAccountById);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 계좌 정보 수정 api
     */
    @PatchMapping(USER_ACCOUNT_REGISTER_URL)
    public ResponseEntity<?> updateAccount(@RequestBody UserAccountRequest userAccountRequest, @PathVariable Long userId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateAccount(userAccountRequest, email);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_ACCOUNT_UPDATE_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 계좌 등록 여부 검증 api
     */
    @GetMapping(USER_ACCOUNT_EXIST_URL)
    public ResponseEntity<?> validateAccountExist(@PathVariable Long userId){
        boolean result = userService.isExistAccount(userId);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.MY_ACCOUNT_EXIST_SUCCESS, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
