//package com.service.user;
//
//import com.common.user.mapper.UserMapper;
//import com.common.user.request.UserSignupRequest;
//import com.common.user.response.UserInformationByAdminResponse;
//import com.common.user.response.UserInformationResponse;
//import com.core.admin_core.user.model.AdminUser;
//import com.core.admin_core.user.repository.AdminUserRepository;
//import com.core.api_core.user.model.User;
//import com.core.api_core.user.model.UserState;
//import com.core.api_core.user.repository.UserRepository;
//import com.common.utils.OptionalUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class UserAdminService {
//    private final UserMapper userMapper;
//    private final AdminUserRepository adminUserRepository;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//
//    public UserInformationByAdminResponse findById(Long userId){
//        User user = OptionalUtil.getOrElseThrow(userRepository.findById(userId), "존재하지 않는 userId dlqslek.");
//        return userMapper.toAdminResponse(user);
//    }
//
//
//}
