package com.api.security.filter;

import com.api.security.exception.InvalidTokenException;
import com.api.security.service.JwtService;
import com.api.security.service.TokenCustomService;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.exception.NoDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.common.utils.SuccessResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";

    private final TokenCustomService tokenCustomService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Optional<String> refreshToken = jwtService.extractRefreshToken(request);

            if (refreshToken.isPresent()) {
                System.out.println("refresh Token Not Empty");
                checkRefreshTokenAndReIssueAccessToken(refreshToken.get(), request, response);
                return;
            }

            if (refreshToken.isEmpty()) {
                System.out.println("refresh Token Empty");
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }

        } catch (InvalidTokenException ex) {
            handleException(response, ex);
        }
    }

    // refreshToken 이 있을때 refreshToken, accessToken 둘다 재발급
    public void checkRefreshTokenAndReIssueAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        try {
            tokenCustomService.processRefreshToken(refreshToken, response);
        }catch (Exception e){
            throw new NoDataException(e.getMessage());
        }
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .ifPresent(accessToken -> {
                    try {
                        // isTokenValid 호출 시 예외 처리
                        jwtService.isTokenValid(accessToken);
                        jwtService.extractEmail(accessToken)
                                .ifPresent(email -> userRepository.findByEmail(email)
                                        .ifPresent(this::saveAuthentication));
                    } catch (Exception e) {
                        log.error("액세스 토큰 유효성 검사 실패: {}", e.getMessage());
                        throw new InvalidTokenException("Access token is invalid");
                    }
                });

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User myUser) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles("Role")
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleException(HttpServletResponse response, InvalidTokenException ex) throws IOException {
        // 401
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new SuccessResponse(false, "Access token is invalid", null)));
    }
}
