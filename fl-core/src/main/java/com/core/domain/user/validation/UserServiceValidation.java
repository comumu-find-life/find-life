package com.core.domain.user.validation;

public interface UserServiceValidation {
    void validateSignUp(String email, String nickName) throws Exception;
}
