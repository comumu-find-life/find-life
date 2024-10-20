package com.redis.user.service;


import com.redis.user.entity.VerificationCode;
import com.redis.user.repository.VerificationCodeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.redis.utils.EmailCodeGenerator.generateVerificationCode;

import java.util.Optional;


@Transactional
@Service
@RequiredArgsConstructor
public class EmailRedisService {


    private final VerificationCodeRepository verificationCodeRepository;
    private final JavaMailSender mailSender;

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
            System.out.println("New Code = " + newCode);
            sendEmail(email, randomCode);
        }
    }

    // 이메일 전송 로직
    private void sendEmail(String toEmail, String verificationCode) {
        String subject = "이메일 인증 코드";
        String body = "인증 코드는 다음과 같습니다: " + verificationCode;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
    }

    public boolean checkVerificationCode(String email, String code) {
        Optional<VerificationCode> storedCodeOpt = verificationCodeRepository.findById(email);

        // Redis에서 저장된 인증 코드 확인
        if (storedCodeOpt.isEmpty() || !storedCodeOpt.get().getCode().equals(code)) {
            return false;
        }

        // 인증이 완료되면 인증 코드 삭제
        verificationCodeRepository.deleteById(email);
        return true;
    }
}