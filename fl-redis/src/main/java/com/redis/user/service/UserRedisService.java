//package com.redis.user.service;
//
//import com.core.api_core.user.model.User;
//import com.core.api_core.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Transactional
//@Service
//@RequiredArgsConstructor
//public class UserRedisService {
//
//
//    @Autowired
//    private final UserRepository userRepository;
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public void saveUserCaching(String email, String refreshToken) {
//        saveRefreshToken(email, refreshToken);
//        findUserByEmail(email);
//    }
//
//    @CachePut(value = "RefreshToken", key = "#email")
//    public String saveRefreshToken(String email, String refreshToken) {
//        return refreshToken;
//    }
//
//
//    @Cacheable(value = "UserPrinciple", key = "#email")
//    public Optional<User> findUserByEmail(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        return user;
//    }
//
//
//}
