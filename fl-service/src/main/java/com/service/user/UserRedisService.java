package com.service.user;

import com.core.user.model.User;
import com.core.user.model.UserPrincple;
import com.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * 사용
 */
@Transactional
@Service
@RequiredArgsConstructor
public class UserRedisService {

    private static final String REFRESH_TOKEN_KEY = "RefreshToken::";

    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public void saveUserCaching(String email, String refreshToken) {
        saveRefreshToken(email, refreshToken);
        findUserByEmail(email);
    }

    @CachePut(value = "RefreshToken", key = "#email")
    public String saveRefreshToken(String email, String refreshToken) {
        return refreshToken;
    }


    @Cacheable(value = "UserPrinciple", key = "#email")
    public Optional<User> findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    public void validateRefreshToken(String email, String refreshToken) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        String rf = (String) values.get(REFRESH_TOKEN_KEY + email);
        String trimmedRF = rf.substring(1, rf.length() - 1);
        if (rf == null || !trimmedRF.equals(refreshToken)) {
            throw new IllegalArgumentException("RefreshToken 검증 실패");
        }
    }


}
