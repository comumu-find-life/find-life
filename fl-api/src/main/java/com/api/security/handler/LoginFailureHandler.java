package com.api.security.handler;

import com.core.exception.AuthException;
import com.core.exception.ErrorResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception){
        throw new AuthException(ErrorResponseCode.FAIL_LOGIN, "로그인에 실패했습니다.");
    }
}