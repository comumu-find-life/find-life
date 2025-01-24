package com.api.security.service;

import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.exception.NotFoundDataException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenCustomService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Transactional
    public void processRefreshToken(String refreshToken, HttpServletResponse response) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundDataException("Refresh 토큰이 존재하지 않습니다."));
        String updateRefreshToken = jwtService.createRefreshToken();
        String updateAccessToken = jwtService.createAccessToken(user.getEmail());

        user.setRefreshToken(updateRefreshToken);
        jwtService.sendAccessAndRefreshToken(response, updateAccessToken, updateRefreshToken);
    }
}