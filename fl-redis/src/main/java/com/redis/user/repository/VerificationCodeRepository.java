package com.redis.user.repository;

import com.redis.user.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
    // 기본적인 CRUD 메서드 사용 가능
}
