package com.service.user;

import com.core.user.model.User;
import com.core.user.repository.UserRepository;
import com.service.user.dto.UserInformationDto;
import com.service.user.dto.UserSignupRequest;
import com.service.user.validation.UserServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserServiceValidation validation;
    private final PasswordEncoder passwordEncoder;


    //create
    public void signUp(UserSignupRequest dto) throws Exception {
        validation.validateSignUp(dto.getEmail(), dto.getNickName());

        User user = dto.toEntity();

        // 비밀번호 인코딩
        String encode = passwordEncoder.encode(dto.getPassword());
        user.passwordEncode(encode);

        userRepository.save(user);
    }

    //read
    public UserInformationDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return toUserInformationDto(user.get());
    }


    //update
    public void update() {
    }

    //delete
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.get());
    }

    private UserInformationDto toUserInformationDto(User user) {
        return UserInformationDto.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .profileEmail(user.getProfileUrl())
                .build();
    }

}
