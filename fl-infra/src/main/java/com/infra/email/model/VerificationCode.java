package com.infra.email.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Getter
@AllArgsConstructor
@RedisHash(value = "VerificationCode", timeToLive = 300)
public class VerificationCode implements Serializable {

    @Id
    private String email;

    private String code;

}