//package com.service.redis;
//
//import com.core.user.model.UserPrincple;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Duration;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class RedisService {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * key 와 value 를 Redis 에 저장한다. 데이터 만료 기간 설정을 하고 싶다면 세번째 파라미터로 Duration 객체를 전달한다.
//     */
//    public <T> void setValues(String key, T data) {
//        ValueOperations<String, Object> values = redisTemplate.opsForValue();
//        values.set(key, data);
//
//    }
//
//    public void setValues(String key, String data, Duration duration) {
//        ValueOperations<String, Object> values = redisTemplate.opsForValue();
//        values.set(key, data, duration);
//    }
//
//    // key 를 이용한 value 조회
//    @Transactional(readOnly = true)
//    public Object getValues(String key) {
//        ValueOperations<String, Object> values = redisTemplate.opsForValue();
//        if (values.get(key) == null) {
//            return "false";
//        }
//        return  values.get(key);
//    }
//    @Transactional(readOnly = true)
//    public Optional<UserPrincple> getUserPrincipal(String key) {
//        ValueOperations<String, Object> values = redisTemplate.opsForValue();
//        System.out.println("ASFFAS");
//        System.out.println(values.get(key));
//        UserPrincple value = (UserPrincple) values.get(key);
//        return Optional.ofNullable(value);
//    }
//
//    //key 를 이용한 데이터 삭제
//    public void deleteValues(String key) {
//        redisTemplate.delete(key);
//    }
//
//    public void expireValues(String key, int timeout) {
//        redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
//    }
//
//    public void setHashOps(String key, Map<String, String> data) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        values.putAll(key, data);
//    }
//
//    @Transactional(readOnly = true)
//    public String getHashOps(String key, String hashKey) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        return Boolean.TRUE.equals(values.hasKey(key, hashKey)) ? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
//    }
//
//    public void deleteHashOps(String key, String hashKey) {
//        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
//        values.delete(key, hashKey);
//    }
//
//    //조회하려는 데이터가 없으면 'false' 반환
//    public boolean checkExistsValue(String value) {
//        return !value.equals("false");
//    }
//}