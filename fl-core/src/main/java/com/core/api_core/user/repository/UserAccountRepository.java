package com.core.api_core.user.repository;

import com.core.api_core.user.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // userId로 UserAccount를 조회하는 메서드
    Optional<UserAccount> findByUserId(Long userId);
}
