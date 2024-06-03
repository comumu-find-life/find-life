package com.service.user;

import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.service.user.dto.*;
import com.service.user.mapper.UserMapper;
import com.service.user.validation.UserServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserServiceValidation validation;
    private final PasswordEncoder passwordEncoder;


    //회원가입 메서드
    public Long signUp(UserSignupRequest dto) throws Exception {
        //검증
        validation.validateSignUp(dto.getEmail(), dto.getNickname());

        User user = userMapper.toEntity(dto);
        // 비밀번호 인코딩
        String encode = passwordEncoder.encode(dto.getPassword());
        user.passwordEncode(encode);
        return userRepository.save(user).getId();
    }


    //회원 id 로 회원 조회
    public UserInformationDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return userMapper.toDto(user.get());
    }

    //회원 email로 회원 조회
    public UserInformationDto findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return userMapper.toDto(user.get());
    }

    // 다른 사용자 프로필 조회 기능
    public UserProfileRequest getUserProfile(Long id){
        Optional<User> user = userRepository.findById(id);
        return userMapper.toProfile(user.get());
    }


    //delete
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.get());
    }



}
