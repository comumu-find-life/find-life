package com.api.security.filter;

import com.api.security.exception.InvalidTokenException;
import com.api.security.service.JwtService;
import com.core.api_core.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.common.utils.SuccessResponse;
import com.redis.user.service.UserRedisService;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";

    private final JwtService jwtService;
    private final UserRedisService userRedisService;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String refreshToken = jwtService.extractRefreshToken(request)
                    .filter(jwtService::isTokenValid)
                    .orElse(null);

            if (refreshToken != null) {
                checkRefreshTokenAndReIssueAccessToken(refreshToken, request, response);
                return;
            }

            if (refreshToken == null) {
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }

        } catch (InvalidTokenException ex) {
            handleException(response, ex);
        }
    }

    // refreshToken 이 있을때 refreshToken, accessToken 둘다 재발급
    public void checkRefreshTokenAndReIssueAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        jwtService.extractAccessToken(request)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> {
                            try {
                                userRedisService.validateRefreshToken(email, refreshToken);
                                String reIssuedRefreshToken = reIssueRefreshToken(email);
                                jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(email), reIssuedRefreshToken);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }));
    }

    private String reIssueRefreshToken(String email) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        userRedisService.saveUserCaching(email, reIssuedRefreshToken);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .ifPresent(accessToken -> {
                    if (jwtService.isTokenValid(accessToken)) {
                        jwtService.extractEmail(accessToken)
                                .ifPresent(email -> userRedisService.findUserByEmail(email)
                                        .ifPresent(this::saveAuthentication));
                    } else {
                        throw new InvalidTokenException("Access token is invalid");
                    }
                });

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User myUser) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private void handleException(HttpServletResponse response, InvalidTokenException ex) throws IOException {
        // 401
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new SuccessResponse(false, "유효하지 않은 토큰: " + ex.getMessage(), null)));
    }
}
