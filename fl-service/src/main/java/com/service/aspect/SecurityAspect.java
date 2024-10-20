package com.service.aspect;

import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.common.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final UserRepository userRepository;

    @Around("@annotation(CurrentUser)")
    public Object getCurrentUserId(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = OptionalUtil.getOrElseThrow(userRepository.findByNickname(name), "존재하지 않는 사용자 닉네임(이름) 입니다.");
        return joinPoint.proceed(new Object[]{user.getId()});
    }
}
