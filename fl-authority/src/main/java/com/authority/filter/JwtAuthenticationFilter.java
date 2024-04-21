package com.authority.filter;

import com.authority.service.JwtService;
import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.service.redis.RedisService;
import com.service.user.UserRedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    }


    // refreshToken 이 있을때 refreshToken, accessToken 둘다 재발급
    public void checkRefreshTokenAndReIssueAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {

        jwtService.extractAccessToken(request)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> {
                            // refresh 토큰 검증
                            try {
                                userRedisService.validateRefreshToken(email, refreshToken);
                                // refreshToken 재발급
                                String reIssuedRefreshToken = reIssueRefreshToken(email);

                                // accessToken, refreshToken 재발급
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
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> userRedisService.findUserByEmail(email)
                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }


    public void saveAuthentication(User myUser) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}