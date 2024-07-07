package com.redis.user;


import com.core.api_core.user.model.User;

import java.util.Optional;


public interface UserRedisService {
    void saveUserCaching(String email, String refreshToken);

    String saveRefreshToken(String email, String refreshToken);

    Optional<User> findUserByEmail(String email);

    void validateRefreshToken(String email, String refreshToken);
}
