package com.core.admin_core.user.repository;

import com.core.admin_core.user.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByEmail(String email);


    Optional<AdminUser> findByRefreshToken(String refreshToken);

}
