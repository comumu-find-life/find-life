package com.service.user.validation;

import com.core.exception.InvalidDataException;
import com.core.api_core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceValidationImpl implements UserServiceValidation {

    private final UserRepository userRepository;

    private static final String ALREADY_EXIST_EMAIL_ERROR = "이미 존재하는 이메일 입니다.";
    private static final String ALREADY_EXIST_NICKNAME_ERROR = "이미 존재하는 닉네임 입니다.";

    @Override
    public void validateSignUp(String email, String nickName) throws Exception {
        if(userRepository.findByEmail(email).isPresent()){
            throw new InvalidDataException(ALREADY_EXIST_EMAIL_ERROR);
        }

        if(userRepository.findByNickname(nickName).isPresent()){
            throw new InvalidDataException(ALREADY_EXIST_NICKNAME_ERROR);
        }
    }

}
