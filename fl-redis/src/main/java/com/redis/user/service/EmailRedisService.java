package com.redis.user.service;


import com.redis.user.entity.VerificationCode;
import com.redis.user.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.redis.utils.EmailCodeGenerator.generateVerificationCode;

import java.util.Optional;


@Transactional
@Service
@RequiredArgsConstructor
public class EmailRedisService {

    private final VerificationCodeRepository verificationCodeRepository;

    public void sendVerificationCode(String email) {
        Optional<VerificationCode> existingCodeOpt = verificationCodeRepository.findById(email);

        // Redis에 저장된 인증 코드가 있는지 확인
        if (existingCodeOpt.isPresent()) {
            // 인증 코드가 이미 존재하면 새로운 코드 생성 후 업데이트
            String randomCode = generateVerificationCode();
            VerificationCode newCode = new VerificationCode(email, randomCode);
            verificationCodeRepository.save(newCode);
        } else {
            // 인증 코드가 없다면 새로 생성하고 저장
            String randomCode = generateVerificationCode();
            VerificationCode newCode = new VerificationCode(email, randomCode);
            verificationCodeRepository.save(newCode);
        }
        // 이메일로 전송하는 로직 추가
    }

    public void checkVerificationCode(String email, String code) {
        Optional<VerificationCode> storedCodeOpt = verificationCodeRepository.findById(email);

        // Redis에서 저장된 인증 코드 확인
        if (storedCodeOpt.isEmpty() || !storedCodeOpt.get().getCode().equals(code)) {
            throw new IllegalArgumentException("인증 코드가 올바르지 않습니다.");
        }

        // 인증이 완료되면 인증 코드 삭제
        verificationCodeRepository.deleteById(email);
    }
}