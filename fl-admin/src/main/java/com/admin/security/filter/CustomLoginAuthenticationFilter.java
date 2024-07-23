package com.admin.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * 로그인 요청
 * AbstractAuthenticationProcessingFilter 는 SpringSecurity 에서 사용되는 필터다. 사용자 인증을 처리하는데 사용되는 필터다.
 * 사용자가 제출한 인증 요청(로그인 폼의 사용자 이름, 암호)를 수신해 이를 처리해 사용자를 인증하는 역할을 한다.
 */
public class CustomLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/v1/api/admin/login";
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final String USERNAME_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    private final ObjectMapper objectMapper;

    public CustomLoginAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        // application/json 형식이 아니면 예외 발생
        if (request.getContentType() == null || (!request.getContentType().equals(CONTENT_TYPE) && !request.getContentType().startsWith(CONTENT_TYPE))) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        //요청받은 messageBody 는 json 형식이므로 ObjectMapper 로 email 과 password 를 추출한다.
        String email = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KEY);

        //인증 처리 대상이 될 UsernamePasswordAuthenticationToken 객체를 email 과 password 로 만든다.
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);//principal 과 credentials 전달

        // attemptAuthentication 메서드에서 인증 처리 객체를 반환하면 LoginService 의 loadUserByUsername 이 동작한다.
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}