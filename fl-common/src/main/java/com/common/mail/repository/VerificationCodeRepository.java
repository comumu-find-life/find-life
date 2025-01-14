package com.common.mail.repository;

import com.common.mail.entity.VerificationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, String> {
}
