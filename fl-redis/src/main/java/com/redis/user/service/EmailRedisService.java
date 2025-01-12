package com.redis.user.service;


import com.core.exception.InvalidDataException;
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
        if (existingCodeOpt.isPresent()) {
            String randomCode = generateVerificationCode();
            VerificationCode newCode = new VerificationCode(email, randomCode);
            verificationCodeRepository.save(newCode);
        } else {
            String randomCode = generateVerificationCode();
            VerificationCode newCode = new VerificationCode(email, randomCode);
            verificationCodeRepository.save(newCode);
            sendEmail(email, randomCode);
        }
    }

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
            throw new InvalidDataException(e.getMessage());
        }
    }

    public boolean checkVerificationCode(String email, String code) {
        Optional<VerificationCode> storedCodeOpt = verificationCodeRepository.findById(email);
        if (storedCodeOpt.isEmpty() || !storedCodeOpt.get().getCode().equals(code)) {
            return false;
        }
        verificationCodeRepository.deleteById(email);
        return true;
    }
}