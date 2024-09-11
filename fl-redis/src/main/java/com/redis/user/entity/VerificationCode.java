package com.redis.user.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


/**
 * RedisHash 는 해당 클래스가 Redis 에 해시 형태로 저장될 엔티티임을 나타냄
 */
@Getter
@Setter
@RedisHash(value = "VerificationCode", timeToLive = 300) // TTL: 5분
public class VerificationCode implements Serializable {

    @Id
    private String email;
    private String code;

    public VerificationCode(String email, String code) {
        this.email = email;
        this.code = code;
    }
}