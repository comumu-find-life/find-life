package com.service.user;

import com.common.user.mapper.UserMapper;
import com.common.user.request.UserSignupRequest;
import com.common.user.response.UserInformationByAdminResponse;
import com.common.user.response.UserInformationResponse;
import com.core.admin_core.user.model.AdminUser;
import com.core.admin_core.user.repository.AdminUserRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserState;
import com.core.api_core.user.repository.UserRepository;
import com.service.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAdminService {
    private final UserMapper userMapper;
    private final AdminUserRepository adminUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUpAdmin(UserSignupRequest dto){
        AdminUser adminUser = AdminUser.builder()
                .email(dto.getEmail())
                .build();
        adminUser.passwordEncode(passwordEncoder.encode(dto.getPassword()));
        return adminUserRepository.save(adminUser).getId();
    }

    /**
     * 모든 회원 조회 메서드 (admin 이 사용)
     */
    public List<UserInformationResponse> findAll(){
        List<UserInformationResponse> response = new ArrayList<>();
        List<User> all = userRepository.findAll();
        all.stream().forEach(user -> {
            response.add(userMapper.toDto(user));
        });
        return response;
    }

    public UserInformationByAdminResponse findById(Long userId){
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), "존재하지 않는 userId dlqslek.");
        return userMapper.toAdminResponse(user);
    }

    public void setUserInactive(Long userId){
        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), "존재하지 않는 userId dlqslek.");
        user.setUserState(UserState.INACTIVE);
    }

}
